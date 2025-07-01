package com.example.backend.controller;

import com.example.backend.dto.CreatePaymentRequest;
import com.example.backend.dto.CreatePaymentResponse;
import com.example.backend.services.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CreatePaymentResponse> createCheckoutSession(@RequestBody CreatePaymentRequest request) throws StripeException {
        CreatePaymentResponse response = stripeService.createCheckoutSession(request);
        return ResponseEntity.ok(response);
    }
}