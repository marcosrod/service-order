package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.order.dto.OrderTrackingResponse;
import com.marcosrod.serviceorder.modules.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderTrackingService {
    void save(Order order, String progressDetails);
    Page<OrderTrackingResponse> findProgressTrackingByOrderId(Pageable pageable, Long orderId);
}
