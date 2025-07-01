package com.example.backend.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private Double amount;
    private Long campaignId;
    private Long contributorId;
}