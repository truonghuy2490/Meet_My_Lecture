package com.springboot.meetMyLecturer.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.meetMyLecturer.config.PaypalPaymentIntent;
import com.springboot.meetMyLecturer.config.PaypalPaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {

        @Autowired
        private APIContext apiContext;

        public Payment createPayment(
                int total,
                String currency,
                PaypalPaymentMethod method,
                PaypalPaymentIntent intent,
                String description,
                String cancelUrl,
                String successUrl) throws PayPalRESTException {
            // Format the total as a double with two decimal places
            double formattedTotal = (double) total / 100.0; // Assuming total is in cents
            Amount amount = new Amount();
            amount.setCurrency(currency);
            amount.setTotal(String.format("%.2f", formattedTotal)); // Format as a double
            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            Payer payer = new Payer();
            payer.setPaymentMethod(method.toString());
            Payment payment = new Payment();
            payment.setIntent(intent.toString());
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);
            apiContext.setMaskRequestId(true);
            return payment.create(apiContext);
        }

        public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecute);
        }


}
