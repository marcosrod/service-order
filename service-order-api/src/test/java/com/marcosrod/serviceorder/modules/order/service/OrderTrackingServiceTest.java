package com.marcosrod.serviceorder.modules.order.service;

import com.marcosrod.serviceorder.modules.common.util.impl.TimeProviderImpl;
import com.marcosrod.serviceorder.modules.order.dto.OrderTrackingResponse;
import com.marcosrod.serviceorder.modules.order.model.OrderTracking;
import com.marcosrod.serviceorder.modules.order.repository.OrderTrackingRepository;
import com.marcosrod.serviceorder.modules.order.service.impl.OrderTrackingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderTrackingServiceTest {

    @InjectMocks
    private OrderTrackingServiceImpl service;
    @Mock
    private OrderTrackingRepository repository;
    @Mock
    private TimeProviderImpl timeProvider;

    @Test
    void save_shouldCallRepositorySave_whenRequested() {
        var savedOrderTracking = getSavedOrderTracking();
        var orderTrackingToSave = getOrderTracking();
        doReturn(savedOrderTracking).when(repository).save(orderTrackingToSave);
        doReturn(savedOrderTracking.getProgressDate()).when(timeProvider).getLocalDateTimeNow();

        assertThatNoException()
                .isThrownBy(() -> service.save(getSavedOrder(), savedOrderTracking.getProgressDetails()));

        verify(repository).save(orderTrackingToSave);
    }

    @Test
    void findProgressTrackingByOrderId_shouldReturnPageTrackingResponse_whenRequested() {
        var pageable = getPageable();

        Page<OrderTracking> orderTrackingPage = new PageImpl<>(Collections.singletonList(getSavedOrderTracking()),
                pageable, TEST_ID_ONE);

        doReturn(orderTrackingPage).when(repository).findByOrderId(pageable, TEST_ID_ONE);

        assertThat(service.findProgressTrackingByOrderId(pageable, TEST_ID_ONE))
                .isEqualTo(getOrderTrackingResponsePage());

        verify(repository).findByOrderId(pageable, TEST_ID_ONE);
    }

    @Test
    void findProgressTrackingByOrderId_shouldReturnEmptyPage_whenNoOrderTrackingFound() {
        var pageable = getPageable();

        Page<OrderTracking> emptyTrackingPage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);
        Page<OrderTrackingResponse> emptyTrackingResponsePage = new PageImpl<>(List.of(),
                pageable, TEST_ID_ONE);

        doReturn(emptyTrackingPage).when(repository).findByOrderId(pageable, TEST_ID_ONE);

        assertThat(service.findProgressTrackingByOrderId(pageable, TEST_ID_ONE))
                .isEqualTo(emptyTrackingResponsePage);

        verify(repository).findByOrderId(pageable, TEST_ID_ONE);
    }
}
