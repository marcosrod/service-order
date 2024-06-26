package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.order.dto.OrderProgressRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderReportResponse;
import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderResponse;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse save(OrderRequest request);
    OrderResponse updateOrderStatus(OrderProgressRequest request);
    Page<OrderReportResponse> getOrderReport(Pageable pageable, OrderFilter filter);
    Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable);
}
