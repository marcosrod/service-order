package com.marcosrod.serviceorder.modules.order.controller;

import com.marcosrod.serviceorder.modules.order.dto.*;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import com.marcosrod.serviceorder.modules.order.service.OrderService;
import com.marcosrod.serviceorder.modules.order.service.OrderTrackingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final OrderTrackingService orderTrackingService;

    @PostMapping
    public OrderResponse save(@RequestBody @Valid OrderRequest request) {
        return service.save(request);
    }

    @PutMapping
    public OrderResponse updateOrderStatus(@RequestBody @Valid OrderProgressRequest request) {
        return service.updateOrderStatus(request);
    }

    @GetMapping("{id}/pending")
    public Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable,
                                                               @PathVariable Long id) {
        return service.findPendingOrdersByTechnicianId(pageable, id);
    }

    @GetMapping("{id}/progress")
    public Page<OrderTrackingResponse> findProgressTrackingByOrderId(Pageable pageable, @PathVariable Long id) {
        return orderTrackingService.findProgressTrackingByOrderId(pageable, id);
    }

    @GetMapping("report")
    public Page<OrderReport> getOrderReport(Pageable pageable, OrderFilter filter) {
        return service.getOrderReport(pageable, filter);
    }
}
