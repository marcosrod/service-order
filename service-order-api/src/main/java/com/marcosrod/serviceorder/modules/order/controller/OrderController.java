package com.marcosrod.serviceorder.modules.order.controller;

import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderResponse;
import com.marcosrod.serviceorder.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderResponse save(@RequestBody OrderRequest request) {
        return service.save(request);
    }

    @GetMapping("{id}/pending")
    public Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable,
                                                               @PathVariable("id") Long id) {
        return service.findPendingOrdersByTechnicianId(pageable, id);
    }
}
