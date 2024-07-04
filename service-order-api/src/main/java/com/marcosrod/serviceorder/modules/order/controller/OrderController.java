package com.marcosrod.serviceorder.modules.order.controller;

import com.marcosrod.serviceorder.modules.order.controller.contract.IOrderController;
import com.marcosrod.serviceorder.modules.order.dto.*;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import com.marcosrod.serviceorder.modules.order.service.OrderService;
import com.marcosrod.serviceorder.modules.order.service.OrderTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderController implements IOrderController {

    private final OrderService service;
    private final OrderTrackingService orderTrackingService;

    @Override
    public OrderResponse save(OrderRequest request) {
        return service.save(request);
    }

    @Override
    public OrderResponse updateOrderStatus(OrderProgressRequest request) {
        return service.updateOrderStatus(request);
    }

    @Override
    public Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable) {
        return service.findPendingOrdersByTechnicianId(pageable);
    }

    @Override
    public Page<OrderTrackingResponse> findProgressTrackingByOrderId(Pageable pageable, Long id) {
        return orderTrackingService.findProgressTrackingByOrderId(pageable, id);
    }

    @Override
    public Page<OrderReportResponse> getOrderReport(Pageable pageable, OrderFilter filter) {
        return service.getOrderReport(pageable, filter);
    }
}
