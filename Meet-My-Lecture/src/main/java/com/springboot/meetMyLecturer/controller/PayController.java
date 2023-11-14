package com.springboot.meetMyLecturer.controller;

import com.paypal.api.payments.Links;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.meetMyLecturer.config.PaypalPaymentIntent;
import com.springboot.meetMyLecturer.config.PaypalPaymentMethod;
import com.springboot.meetMyLecturer.modelDTO.PaymentRequestDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.impl.PaypalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/payment")
public class PayController {

    @Autowired
    PaypalService paypalService;
    @Autowired
    UserRepository userRepository;

    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/cancel";
    public static final String HOME_PAY = "/home";
    @GetMapping(HOME_PAY)
    public String home() {
        try {
            // Load the HTML file from the resources
            Resource resource = new ClassPathResource("templates/home.html");

            // Read the content of the HTML file
            byte[] htmlBytes = StreamUtils.copyToByteArray(resource.getInputStream());

            // Convert the byte array to a string
            String htmlContent = new String(htmlBytes);

            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading HTML file";
        }
    }
    @PostMapping
    public ResponseEntity<String> createPayment(
            @RequestBody PaymentRequestDTO paymentRequest
    ) throws IOException {
        Resource resource = new ClassPathResource("templates/home.html");
        byte[] htmlBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        String htmlContent = new String(htmlBytes);
        try {

            Payment payment = paypalService.createPayment(
                    paymentRequest.getPrice(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    paymentRequest.getDescription(),
                    "http://localhost:8080/api/v1/payment" + CANCEL_URL,
                    "http://localhost:8080/api/v1/payment" + SUCCESS_URL
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(htmlContent);
    }


    @GetMapping(CANCEL_URL)
    public String cancelPayment() {
        try {
            // Load the HTML file from the resources
            Resource resource = new ClassPathResource("templates/cancel.html");

            // Read the content of the HTML file
            byte[] htmlBytes = StreamUtils.copyToByteArray(resource.getInputStream());

            // Convert the byte array to a string
            String htmlContent = new String(htmlBytes);

            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading HTML file";
        }
    }

    @GetMapping(SUCCESS_URL)
    public String successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpServletRequest request
    ) throws IOException {
        Resource resource = new ClassPathResource("templates/home.html");
        byte[] htmlBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        String htmlContent = new String(htmlBytes);
        try {
            try {
                Payment payment = paypalService.executePayment(paymentId, payerId);
                System.out.println(payment.toJSON());
                if (payment.getState().equals("approved")) {
                    resource = new ClassPathResource("templates/success.html");
                    htmlBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                    htmlContent = new String(htmlBytes);
                    return htmlContent;
                }
            }catch (PayPalRESTException e){
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading HTML file";
        }
        return htmlContent;

    }

   /* @PostMapping(SUCCESS_URL)
    public ResponseEntity<String> successPaymentPost(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            *//*Long userId = userRepository.findUserIdByEmail(Constant.EMAIL);
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
            );*//*

            if (payment.getState().equals("approved")) {
//                user.setStatus(Constant.OPEN);
                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
    }*/


    private String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }

}
