//package com.insurance.policy.controller;
//
//import com.insurance.policy.dto.payment.PaymentRequest;
//import com.insurance.policy.dto.payment.PaymentResponse;
//import com.insurance.policy.service.PaymentService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * REST controller for Payment operations.
// */
//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private final PaymentService paymentService;
//
//    /**
//     * Make payment.
//     *
//     * POST /api/payments
//     */
//    @PostMapping
//    public ResponseEntity<PaymentResponse> makePayment(
//            @Valid @RequestBody PaymentRequest request) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(paymentService.makePayment(request));
//    }
//
//    /**
//     * Get all payments for a policy.
//     *
//     * GET /api/payments/policy/{policyId}
//     */
//    @GetMapping("/policy/{policyId}")
//    public ResponseEntity<List<PaymentResponse>> getPaymentsByPolicy(
//            @PathVariable Long policyId) {
//
//        return ResponseEntity.ok(
//                paymentService.getPaymentsByPolicy(policyId)
//        );
//    }
//
//    /**
//     * Get payment by ID.
//     *
//     * GET /api/payments/{id}
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<PaymentResponse> getPaymentById(
//            @PathVariable Long id) {
//
//        return ResponseEntity.ok(
//                paymentService.getPaymentById(id)
//        );
//    }
//}




package com.insurance.policy.controller;

import com.insurance.policy.dto.payment.PaymentRequest;
import com.insurance.policy.dto.payment.PaymentResponse;
import com.insurance.policy.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * POST /api/payments
     */
    @PostMapping("/api/payments")
    public ResponseEntity<PaymentResponse> makePayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.makePayment(request));
    }

    /**
     * GET /api/policies/{policyId}/payments
     */
    @GetMapping("/api/policies/{policyId}/payments")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByPolicy(
            @PathVariable Long policyId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByPolicy(policyId)
        );
    }

    /**
     * GET /api/payments/{id}
     */
    @GetMapping("/api/payments/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }
}