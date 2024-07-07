package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.common.util.impl.TimeProviderImpl;
import com.marcosrod.serviceorder.config.security.authentication.service.impl.JwtServiceImpl;
import com.marcosrod.serviceorder.modules.client.service.ClientServiceImpl;
import com.marcosrod.serviceorder.modules.equipment.service.impl.EquipmentServiceImpl;
import com.marcosrod.serviceorder.modules.order.dto.OrderReportResponse;
import com.marcosrod.serviceorder.modules.order.dto.OrderResponse;
import com.marcosrod.serviceorder.modules.order.model.Order;
import com.marcosrod.serviceorder.modules.order.repository.OrderRepository;
import com.marcosrod.serviceorder.modules.order.service.impl.OrderServiceImpl;
import com.marcosrod.serviceorder.modules.order.service.impl.OrderTrackingServiceImpl;
import com.marcosrod.serviceorder.modules.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getSavedClient;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getSavedEquipment;
import static com.marcosrod.serviceorder.modules.order.enums.OrderStatus.F;
import static com.marcosrod.serviceorder.modules.order.enums.OrderStatus.P;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.*;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getOrderReportResponsePage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl service;
    @Mock
    private OrderRepository repository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private EquipmentServiceImpl equipmentService;
    @Mock
    private OrderTrackingServiceImpl orderTrackingService;
    @Mock
    private TimeProviderImpl timeProvider;
    @Mock
    private JwtServiceImpl jwtService;

    @Test
    void save_shouldReturnOrderResponse_whenValidRequest() {
        var orderToSave = getOrder();
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(true).when(userService).existsByIds(userIds);
        doReturn(Optional.empty()).when(repository)
                .findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        doReturn(getSavedClient()).when(clientService).findById(TEST_ID_ONE);
        doReturn(getSavedEquipment()).when(equipmentService).findById(TEST_ID_ONE);
        doReturn(orderToSave.getCreationDate()).when(timeProvider).getLocalDateTimeNow();
        doReturn(getSavedOrder()).when(repository).save(orderToSave);


        assertThat(service.save(getOrderRequest()))
                .isEqualTo(getOrderResponse());

        verify(userService).existsByIds(userIds);
        verify(repository).findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        verify(clientService).findById(TEST_ID_ONE);
        verify(equipmentService).findById(TEST_ID_ONE);
        verify(repository).save(orderToSave);
    }

    @Test
    void save_shouldThrowValidationException_whenInvalidUserIds() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(false).when(userService).existsByIds(userIds);


        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(getOrderRequest()))
                .withMessage(ValidationError.USERS_NOT_FOUND.getMessage());

        verify(userService).existsByIds(userIds);
        verify(repository, never()).findByClientIdAndEquipmentIdAndStatusNot(anyLong(), anyLong(), any());
        verify(clientService, never()).findById(anyLong());
        verify(equipmentService, never()).findById(anyLong());
        verify(repository, never()).save(any());
    }

    @Test
    void save_shouldThrowValidationException_whenDuplicatedClientOrder() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(true).when(userService).existsByIds(userIds);
        doReturn(Optional.of(getSavedOrder())).when(repository)
                .findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);


        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(getOrderRequest()))
                .withMessage(ValidationError.ORDER_ALREADY_EXISTS.getMessage());

        verify(userService).existsByIds(userIds);
        verify(repository).findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        verify(clientService, never()).findById(anyLong());
        verify(equipmentService, never()).findById(anyLong());
        verify(repository, never()).save(any());
    }

    @Test
    void save_shouldThrowValidationException_whenClientIdDoesNotExists() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(true).when(userService).existsByIds(userIds);
        doReturn(Optional.empty()).when(repository)
                .findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        doThrow(new ValidationException(ValidationError.CLIENT_NOT_FOUND.getMessage()))
                .when(clientService).findById(TEST_ID_ONE);


        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(getOrderRequest()))
                .withMessage(ValidationError.CLIENT_NOT_FOUND.getMessage());

        verify(userService).existsByIds(userIds);
        verify(repository).findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        verify(clientService).findById(TEST_ID_ONE);
        verify(equipmentService, never()).findById(anyLong());
        verify(repository, never()).save(any());
    }

    @Test
    void save_shouldThrowValidationException_whenEquipmentIdDoesNotExists() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(true).when(userService).existsByIds(userIds);
        doReturn(Optional.empty()).when(repository)
                .findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        doReturn(getSavedClient()).when(clientService).findById(TEST_ID_ONE);
        doThrow(new ValidationException(ValidationError.EQUIPMENT_NOT_FOUND.getMessage()))
                .when(equipmentService).findById(TEST_ID_ONE);


        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(getOrderRequest()))
                .withMessage(ValidationError.EQUIPMENT_NOT_FOUND.getMessage());

        verify(userService).existsByIds(userIds);
        verify(repository).findByClientIdAndEquipmentIdAndStatusNot(TEST_ID_ONE, TEST_ID_ONE, F);
        verify(clientService).findById(TEST_ID_ONE);
        verify(equipmentService).findById(TEST_ID_ONE);
        verify(repository, never()).save(any());
    }

    @Test
    void updateOrderStatus_shouldReturnOrderResponse_whenRequested() {
        doReturn(Optional.of(getSavedOrder())).when(repository).findById(TEST_ID_ONE);

        assertThat(service.updateOrderStatus(getProgressRequest()))
                .isEqualTo(getOrderResponse());

        verify(repository).findById(TEST_ID_ONE);
    }

    @Test
    void updateOrderStatus_shouldThrowValidationException_whenOrderDoesNotExists() {
        doReturn(Optional.empty()).when(repository).findById(TEST_ID_ONE);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.updateOrderStatus(getProgressRequest()))
                .withMessage(ValidationError.ORDER_NOT_FOUND.getMessage());

        verify(repository).findById(TEST_ID_ONE);
    }

    @Test
    void getOrderReport_shouldReturnPageOrderReportResponse_whenRequested() {
        var savedOrder = getSavedOrder();
        var filter = getOrderFilter();
        var predicate = filter.toPredicate().build();
        var pageable = getPageable();

        Page<Order> ordersPage = new PageImpl<>(Collections.singletonList(savedOrder), pageable, TEST_ID_ONE);

        doReturn(ordersPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getOrderReport(pageable, filter))
                .isEqualTo(getOrderReportResponsePage());

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void getOrderReport_shouldReturnEmptyPage_whenNoOrdersFound() {
        var filter = getOrderFilter();
        var predicate = filter.toPredicate().build();
        var pageable = getPageable();

        Page<Order> emptyOrderPage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);
        Page<OrderReportResponse> emptyReportResponsePage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);

        doReturn(emptyOrderPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getOrderReport(pageable, filter))
                .isEqualTo(emptyReportResponsePage);

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void findPendingOrdersByTechnicianId_shouldReturnPageOrderResponse_whenRequested() {
        var pageable = getPageable();

        Page<Order> ordersPage = new PageImpl<>(Collections.singletonList(getSavedOrder()), pageable, TEST_ID_ONE);

        doReturn(ordersPage).when(repository).findAllByTechnicianIdAndStatus(pageable, TEST_ID_ONE, P);
        doReturn(TEST_ID_ONE).when(jwtService).getAuthenticatedUserId();

        assertThat(service.findPendingOrdersByTechnicianId(pageable))
                .isEqualTo(getOrderResponsePage());

        verify(repository).findAllByTechnicianIdAndStatus(pageable, TEST_ID_ONE, P);
        verify(jwtService).getAuthenticatedUserId();
    }

    @Test
    void findPendingOrdersByTechnicianId_shouldEmptyPage_whenNoOrdersFound() {
        var pageable = getPageable();

        Page<Order> emptyOrdersPage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);
        Page<OrderResponse> emptyOrdersResponsePage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);

        doReturn(emptyOrdersPage).when(repository).findAllByTechnicianIdAndStatus(pageable, TEST_ID_ONE, P);
        doReturn(TEST_ID_ONE).when(jwtService).getAuthenticatedUserId();

        assertThat(service.findPendingOrdersByTechnicianId(pageable))
                .isEqualTo(emptyOrdersResponsePage);

        verify(repository).findAllByTechnicianIdAndStatus(pageable, TEST_ID_ONE, P);
        verify(jwtService).getAuthenticatedUserId();
    }
}
