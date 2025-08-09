package com.next.app.api.payment.controller;

import com.next.app.api.payment.dto.PaymentRequestDto;
import com.next.app.api.payment.dto.PaymentResponseDto;
import com.next.app.api.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Controller", description = "결제 관련 API - 주문 결제 처리 및 취소")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "결제 처리", description = "주문 ID와 결제 수단 정보를 받아 결제를 처리합니다. 카드 또는 은행 계좌 결제 지원")
    public ResponseEntity<PaymentResponseDto> pay(@RequestBody PaymentRequestDto req) {
        PaymentResponseDto resp = paymentService.processPayment(
                req.getOrderId(), req.getPaymentMethod(), req.getPaymentInfo());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{paymentId}")
    @Operation(summary = "결제 취소", description = "결제 ID를 받아 결제를 취소하고 주문 상태를 변경합니다.")
    public ResponseEntity<PaymentResponseDto> cancel(@PathVariable Long paymentId) {
        PaymentResponseDto resp = paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "결제 정보 조회", description = "결제 ID로 결제 상세 정보를 조회합니다.")
    public ResponseEntity<PaymentResponseDto> get(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


