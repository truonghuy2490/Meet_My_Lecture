package com.springboot.meetMyLecturer.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.meetMyLecturer.config.PaypalPaymentIntent;
import com.springboot.meetMyLecturer.config.PaypalPaymentMethod;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.PaymentRequestDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.impl.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/payment")
public class PayController {

    @Autowired
    PaypalService paypalService;
    @Autowired
    UserRepository userRepository;

    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/cancel";

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            Payment payment = paypalService.createPayment(
                    paymentRequest.getPrice(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    paymentRequest.getDescription(),
                    paymentRequest.getUrl() +CANCEL_URL,
                    paymentRequest.getUrl() +SUCCESS_URL
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
    }

    @GetMapping(CANCEL_URL)
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("Payment canceled");
    }

    @PostMapping(SUCCESS_URL)
    public ResponseEntity<String> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            Long userId = userRepository.findUserIdByEmail(Constant.EMAIL);
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new ResourceNotFoundException("User","id",String.valueOf(userId))
            );

            if (payment.getState().equals("approved")) {
                user.setStatus(Constant.OPEN);
                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
    }




}
