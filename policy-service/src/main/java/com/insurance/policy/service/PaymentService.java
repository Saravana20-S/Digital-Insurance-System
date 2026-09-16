package com.insurance.policy.service;

import com.insurance.policy.dto.event.PolicyActivatedEvent;
import com.insurance.policy.dto.payment.PaymentRequest;
import com.insurance.policy.dto.payment.PaymentResponse;
import com.insurance.policy.entity.Payment;
import com.insurance.policy.entity.PaymentStatus;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.PolicyStatus;
import com.insurance.policy.entity.Premium;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.messaging.PolicyEventProducer;
import com.insurance.policy.repository.PaymentRepository;
import com.insurance.policy.repository.PolicyRepository;
import com.insurance.policy.repository.PremiumRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Business logic for Payment operations.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PolicyRepository policyRepository;

    private final PremiumRepository premiumRepository;

    private final PolicyEventProducer policyEventProducer;

    /**
     * Simulate a successful payment.
     *
     * For this project this is a payment simulation.
     */
    @Transactional
    public PaymentResponse makePayment(
            PaymentRequest request) {

        Long policyId = request.getPolicyId();

        Policy policy =
                policyRepository.findById(policyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Policy not found with id: " + policyId
                                )
                        );

        if (policy.getStatus() !=
                PolicyStatus.PAYMENT_PENDING) {

            throw new IllegalStateException(
                    "Payment cannot be made for policy with status: "
                            + policy.getStatus()
            );
        }

        Premium premium =
                premiumRepository
                        .findByPolicyId(policyId)
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Premium not found for policy id: "
                                                + policyId
                                )
                        );

        /*
         * Validate payment amount.
         */
        if (request.getAmount()
                .compareTo(premium.getAmount()) != 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be exactly "
                            + premium.getAmount()
            );
        }

        /*
         * Simulate successful payment.
         */
        Payment payment =
                Payment.builder()
                        .policy(policy)
                        .amount(request.getAmount())
                        .status(PaymentStatus.SUCCESS)
                        .transactionReference(
                                "TXN-"
                                        + UUID.randomUUID()
                                        .toString()
                                        .substring(0, 8)
                        )
                        .build();

        Payment savedPayment =
                paymentRepository.save(payment);

        /*
         * Mark premium as paid.
         */
        premium.setPaid(true);

        premiumRepository.save(premium);

        /*
         * Activate policy after successful first payment.
         */
        policy.setStatus(
                PolicyStatus.ACTIVE
        );

        policyRepository.save(policy);

        PolicyActivatedEvent event =
                PolicyActivatedEvent.builder()
                        .policyId(policy.getId())
                        .customerId(policy.getCustomerId())
                        .planId(policy.getInsurancePlan().getId())
                        .planName(
                                policy.getInsurancePlan().getName()
                        )
                        .message(
                                "Your insurance policy has been activated successfully."
                        )
                        .build();

        policyEventProducer.publishPolicyActivated(event);

        return toResponse(savedPayment);
    }

    /**
     * Get payments for policy.
     */
    @Transactional(readOnly = true)
    public java.util.List<PaymentResponse>
    getPaymentsByPolicy(Long policyId) {

        return paymentRepository
                .findByPolicyId(policyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Convert Payment entity to response DTO.
     */
    private PaymentResponse toResponse(
            Payment payment) {

        return PaymentResponse.builder()
                .id(payment.getId())
                .policyId(
                        payment.getPolicy().getId()
                )
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .transactionReference(
                        payment.getTransactionReference()
                )
                .paymentDate(
                        payment.getPaymentDate()
                )
                .build();
    }


    /**
     * Get payment by ID.
     */
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        return toResponse(payment);
    }


}