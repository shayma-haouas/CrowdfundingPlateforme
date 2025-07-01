package com.example.backend.services;

import com.example.backend.dto.CreatePaymentRequest;
import com.example.backend.dto.CreatePaymentResponse;
import com.example.backend.entities.Campagne;
import com.example.backend.entities.Contribution;
import com.example.backend.entities.Utilisateur;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.CampagneRepository;
import com.example.backend.repository.ContributionRepository;
import com.example.backend.repository.UtilisateurRepository;
import com.example.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StripeServiceImpl implements StripeService {

    private final ContributionRepository contributionRepository;
    private final CampagneRepository campagneRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final String webhookSecret;

    public StripeServiceImpl(ContributionRepository contributionRepository,
                             CampagneRepository campagneRepository,
                             UtilisateurRepository utilisateurRepository,
                             @Value("${stripe.webhook-secret}") String webhookSecret) {
        this.contributionRepository = contributionRepository;
        this.campagneRepository = campagneRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.webhookSecret = webhookSecret;
    }

    @Override
    public CreatePaymentResponse createCheckoutSession(CreatePaymentRequest request) throws StripeException {
        Utilisateur contributeur = utilisateurRepository.findById(request.getContributorId()).orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
        Campagne campagne = campagneRepository.findById(request.getCampaignId()).orElseThrow(() -> new ResourceNotFoundException("Campagne introuvable"));

        long amountInCents = (long) (request.getAmount() * 100);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:3000/campaigns/" + request.getCampaignId())
                .setCustomerEmail(contributeur.getEmail())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(amountInCents)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Contribution à la campagne: " + campagne.getTitre())
                                        .build())
                                .build())
                        .build())
                .putMetadata("campaignId", request.getCampaignId().toString())
                .putMetadata("contributorId", request.getContributorId().toString())
                .build();

        Session session = Session.create(params);
        return new CreatePaymentResponse(session.getUrl());
    }

    @Override
    @Transactional
    public void handleWebhookEvent(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, this.webhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getData().getObject();
                if ("paid".equals(session.getPaymentStatus())) {
                    fulfillContribution(session);
                }
            } else {
                log.info("Événement Stripe non géré: {}", event.getType());
            }

        } catch (Exception e) {
            log.error("Erreur lors du traitement du webhook Stripe", e);
            throw new RuntimeException(e);
        }
    }

    private void fulfillContribution(Session session) {
        Long campaignId = Long.parseLong(session.getMetadata().get("campaignId"));
        Long contributorId = Long.parseLong(session.getMetadata().get("contributorId"));
        Double amount = (double) (session.getAmountTotal() / 100);

        Campagne campagne = campagneRepository.findById(campaignId).orElseThrow(() -> new ResourceNotFoundException("Campagne non trouvée (depuis webhook)"));
        Utilisateur contributeur = utilisateurRepository.findById(contributorId).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé (depuis webhook)"));

        Contribution contribution = Contribution.builder()
                .montant(amount)
                .campagne(campagne)
                .contributeur(contributeur)
                .build();
        contributionRepository.save(contribution);

        campagne.setMontantCollecte(campagne.getMontantCollecte() + amount);
        campagneRepository.save(campagne);

        log.info("Contribution de {}€ enregistrée pour la campagne '{}' par l'utilisateur '{}'.", amount, campagne.getTitre(), contributeur.getEmail());
    }
}