package com.marcosrod.serviceorder.modules.order.repository;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import com.marcosrod.serviceorder.modules.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByTechnicianIdAndStatus(Pageable pageable, Long id, OrderStatus status);
}
