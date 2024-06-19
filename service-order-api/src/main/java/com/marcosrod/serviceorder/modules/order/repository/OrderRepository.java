package com.marcosrod.serviceorder.modules.order.repository;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import com.marcosrod.serviceorder.modules.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
    Page<Order> findAllByTechnicianIdAndStatus(Pageable pageable, Long id, OrderStatus status);
    Optional<Order> findByClientIdAndEquipmentIdAndStatusNot(Long clientId, Long equipmentId, OrderStatus status);
}
