package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.order.dto.OrderTrackingResponse;
import com.marcosrod.serviceorder.modules.order.model.Order;
import com.marcosrod.serviceorder.modules.order.model.OrderTracking;
import com.marcosrod.serviceorder.modules.order.repository.OrderTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderTrackingService {

    private final OrderTrackingRepository repository;

    public void save(Order order, String progressDetails) {
        repository.save(OrderTracking.of(order, progressDetails));
    }

    public Page<OrderTrackingResponse> findProgressTrackingByOrderId(Pageable pageable, Long orderId) {
        return repository.findByOrderId(pageable, orderId)
                .map(OrderTrackingResponse::of);
    }
}
