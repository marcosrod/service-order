package com.marcosrod.serviceorder.modules.order.service.impl;

import com.marcosrod.serviceorder.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.client.service.ClientService;
import com.marcosrod.serviceorder.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import com.marcosrod.serviceorder.modules.order.dto.OrderReport;
import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderResponse;
import com.marcosrod.serviceorder.modules.order.dto.OrderProgressRequest;
import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import com.marcosrod.serviceorder.modules.order.model.Order;
import com.marcosrod.serviceorder.modules.order.repository.OrderRepository;
import com.marcosrod.serviceorder.modules.order.service.OrderService;
import com.marcosrod.serviceorder.modules.order.service.OrderTrackingService;
import com.marcosrod.serviceorder.modules.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final UserService userService;
    private final ClientService clientService;
    private final EquipmentService equipmentService;
    private final OrderTrackingService orderTrackingService;

    public OrderResponse save(OrderRequest request) {
        validateUserIds(List.of(request.receptionistId(), request.technicianId()));
        validateDuplicatedClientOrder(request.clientId(), request.equipmentId());
        var client = clientService.findById(request.clientId());
        var equipment = equipmentService.findById(request.equipmentId());

        var savedOrder = repository.save(Order.of(request, client, equipment));

        return OrderResponse.of(savedOrder);
    }

    private void validateDuplicatedClientOrder(Long clientId, Long equipmentId) {
        if (repository.findByClientIdAndEquipmentIdAndStatusNot(clientId, equipmentId,
                OrderStatus.F).isPresent()) {
            throw new ValidationException(ValidationError.ORDER_ALREADY_EXISTS.getMessage());
        }
    }

    @Transactional
    public OrderResponse updateOrderStatus(OrderProgressRequest request) {
        var order = findById(request.orderId());
        order.updateOrderStatus(request.status());
        orderTrackingService.save(order, request.progressDetails());

        return OrderResponse.of(order);
    }

    public Page<OrderReport> getOrderReport(Pageable pageable, OrderFilter filter) {
        return repository.findAll(filter.toPredicate().build(), pageable)
                .map(OrderReport::of);
    }

    private Order findById(Long orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new ValidationException(ValidationError.ORDER_NOT_FOUND.getMessage()));
    }

    public Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable, Long id) {
        return repository.findAllByTechnicianIdAndStatus(pageable, id, OrderStatus.P)
                .map(OrderResponse::of);
    }

    private void validateUserIds(List<Long> employeeIds) {
        if (!userService.existsByIds(employeeIds)) {
            throw new ValidationException(ValidationError.USER_NOT_FOUND.getMessage());
        }
    }
}
