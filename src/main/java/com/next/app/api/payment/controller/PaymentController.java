package com.next.app.api.payment.controller;

import com.next.app.api.payment.dto.PaymentRequestDto;
import com.next.app.api.payment.dto.PaymentResponseDto;
import com.next.app.api.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> pay(@RequestBody PaymentRequestDto req) {
        PaymentResponseDto resp = paymentService.processPayment(req.getOrderId(), req.getPaymentMethod(), req.getPaymentInfo());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> cancel(@PathVariable Long paymentId) {
        PaymentResponseDto resp = paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> get(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

