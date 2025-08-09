package com.next.app.api.payment.service;

import com.next.app.api.order.entity.Order;
import com.next.app.api.order.service.OrderService;
import com.next.app.api.payment.dto.PaymentResponseDto;
import com.next.app.api.payment.entity.Payment;
import com.next.app.api.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService; // 인터페이스 의존

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public PaymentResponseDto processPayment(Long orderId, String paymentMethod, String paymentInfo) {
        Order order = orderService.getOrder(orderId);
        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Order is not in PENDING state: " + order.getStatus());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod.toUpperCase());
        payment.setPaidAt(LocalDateTime.now());

        if ("CARD".equalsIgnoreCase(paymentMethod)) {
            payment.setCardNumber(maskTail(paymentInfo, 4));
        } else if ("BANK".equalsIgnoreCase(paymentMethod)) {
            payment.setBankAccount(maskTail(paymentInfo, 4));
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }

        payment = paymentRepository.save(payment);

        // 주문 상태 변경은 OrderService에게 위임
        orderService.setOrderPaid(orderId);

        return toDto(payment);
    }

    @Transactional
    public PaymentResponseDto cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        Order order = payment.getOrder();
        if (!"PAID".equals(order.getStatus())) {
            throw new IllegalStateException("Only PAID orders can be cancelled");
        }

        // 주문 상태 변경 (OrderService에게 위임)
        orderService.setOrderCancelled(order.getId());

        // 재고 복구: order.getOrderItems() 존재시 처리 (Order 엔티티에서 relation 필요)
        // 예: order.getOrderItems().forEach(i -> i.getProduct().increaseQuantity(i.getQuantity()));

        payment.setCancelledAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        return toDto(payment);
    }

    @Transactional(readOnly = true)
    public Optional<PaymentResponseDto> getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).map(this::toDto);
    }

    // 마스킹 유틸: 마지막 n자리만 보이고 나머지는 '*'로 채움
    private String maskTail(String input, int visibleTail) {
        if (input == null) return null;
        int len = input.length();
        if (len <= visibleTail) return input;
        return "*".repeat(len - visibleTail) + input.substring(len - visibleTail);
    }

    private PaymentResponseDto toDto(Payment p) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(p.getId());
        dto.setOrderId(p.getOrder().getId());
        dto.setPaymentMethod(p.getPaymentMethod());
        dto.setCardNumber(p.getCardNumber());
        dto.setBankAccount(p.getBankAccount());
        dto.setPaidAt(p.getPaidAt());
        dto.setCancelledAt(p.getCancelledAt());
        return dto;
    }
}
