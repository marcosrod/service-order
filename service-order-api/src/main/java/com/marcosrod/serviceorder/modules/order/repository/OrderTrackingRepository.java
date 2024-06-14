package com.marcosrod.serviceorder.modules.order.repository;

import com.marcosrod.serviceorder.modules.order.model.OrderTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {
    Page<OrderTracking> findByOrderId(Pageable pageable, Long id);
}
