package com.example.backend.services;

import com.example.backend.dto.CreatePaymentRequest;
import com.example.backend.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;

public interface StripeService {
    CreatePaymentResponse createCheckoutSession(CreatePaymentRequest request) throws StripeException;
    void handleWebhookEvent(String payload, String sigHeader);
}