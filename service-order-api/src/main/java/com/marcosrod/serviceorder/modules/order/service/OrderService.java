package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.client.service.ClientService;
import com.marcosrod.serviceorder.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderResponse;
import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import com.marcosrod.serviceorder.modules.order.model.Order;
import com.marcosrod.serviceorder.modules.order.repository.OrderRepository;
import com.marcosrod.serviceorder.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final UserService userService;
    private final ClientService clientService;
    private final EquipmentService equipmentService;

    public OrderResponse save(OrderRequest request) {
        validateEmployeeIds(List.of(request.receptionistId(), request.technicianId()));
        var client = clientService.findById(request.clientId());
        var equipment = equipmentService.findById(request.equipmentId());

        var savedOrder = repository.save(Order.of(request, client, equipment));

        return OrderResponse.of(savedOrder);
    }

    public Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable, Long id) {
        return repository.findAllByTechnicianIdAndStatus(pageable, id, OrderStatus.P)
                .map(OrderResponse::of);
    }

    private void validateEmployeeIds(List<Long> employeeIds) {
        if (!userService.existsByIds(employeeIds)) {
            throw new ValidationException("One or more requested employees doesn't exists.");
        }
    }
}
