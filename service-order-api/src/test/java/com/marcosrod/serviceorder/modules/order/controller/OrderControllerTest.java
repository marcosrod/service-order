package com.marcosrod.serviceorder.modules.order.controller;

import com.marcosrod.serviceorder.modules.order.dto.OrderProgressRequest;
import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.service.impl.OrderServiceImpl;
import com.marcosrod.serviceorder.modules.order.service.impl.OrderTrackingServiceImpl;
import com.marcosrod.serviceorder.modules.user.enums.Role;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.*;
import static com.marcosrod.serviceorder.modules.common.helper.JsonHelper.asJsonString;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.TEST_USER_EMAIL;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.getJwtToken;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.*;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    private static final String API_URI = "/api/orders";
    private static final String PENDING_ENDPOINT = "/pending";
    private static final String PROGRESS_ENDPOINT = "/%d/progress";
    private static final String REPORT_ENDPOINT = "/report";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderServiceImpl service;
    @MockBean
    private OrderTrackingServiceImpl orderTrackingService;

    @SneakyThrows
    @Test
    void save_shouldReturnOkAndOrderResponse_whenRequested() {
        var orderRequest = getOrderRequest();
        var orderResponse = getOrderResponse();

        doReturn(orderResponse).when(service).save(orderRequest);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponse.id()))
                .andExpect(jsonPath("$.clientId").value(orderResponse.clientId()))
                .andExpect(jsonPath("$.clientName").value(orderResponse.clientName()))
                .andExpect(jsonPath("$.equipmentId").value(orderResponse.equipmentId()))
                .andExpect(jsonPath("$.equipmentProblem").value(orderResponse.equipmentProblem()))
                .andExpect(jsonPath("$.receptionistId").value(orderResponse.receptionistId()))
                .andExpect(jsonPath("$.technicianId").value(orderResponse.technicianId()))
                .andExpect(jsonPath("$.status").value(orderResponse.status()))
                .andExpect(jsonPath("$.creationDate").value("01/01/2100 08:00:00"));

        verify(service).save(orderRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnBadRequestAndValidationError_whenInvalidRequest() {
        var orderRequest = getOrderRequest();
        var invalidOrderRequest = new OrderRequest(null, orderRequest.clientId(),
                orderRequest.equipmentId(), orderRequest.technicianId(), orderRequest.equipmentProblem());

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Error parsing the field: receptionistId -> must not be null"));

        verify(service, never()).save(invalidOrderRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnForbidden_whenUserHasNoPermission() {
        var orderRequest = getOrderRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderRequest)))
                .andExpect(status().isForbidden());

        verify(service, never()).save(orderRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        var orderRequest = getOrderRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderRequest)))
                .andExpect(status().isUnauthorized());

        verify(service, never()).save(orderRequest);
    }

    @SneakyThrows
    @Test
    void updateOrderStatus_shouldReturnOkAndOrderResponse_whenRequested() {
        var progressRequest = getProgressRequest();
        var orderResponse = getOrderResponse();

        doReturn(orderResponse).when(service).updateOrderStatus(progressRequest);

        mvc.perform(MockMvcRequestBuilders.put(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(progressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponse.id()))
                .andExpect(jsonPath("$.clientId").value(orderResponse.clientId()))
                .andExpect(jsonPath("$.clientName").value(orderResponse.clientName()))
                .andExpect(jsonPath("$.equipmentId").value(orderResponse.equipmentId()))
                .andExpect(jsonPath("$.equipmentProblem").value(orderResponse.equipmentProblem()))
                .andExpect(jsonPath("$.receptionistId").value(orderResponse.receptionistId()))
                .andExpect(jsonPath("$.technicianId").value(orderResponse.technicianId()))
                .andExpect(jsonPath("$.status").value(orderResponse.status()))
                .andExpect(jsonPath("$.creationDate").value("01/01/2100 08:00:00"));

        verify(service).updateOrderStatus(progressRequest);
    }

    @SneakyThrows
    @Test
    void updateOrderStatus_shouldReturnBadRequestAndValidationError_whenInvalidRequest() {
        var progressRequest = getProgressRequest();
        var invalidRequest = new OrderProgressRequest(null, progressRequest.status(),
                progressRequest.progressDetails());

        mvc.perform(MockMvcRequestBuilders.put(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Error parsing the field: orderId -> must not be null"));

        verify(service, never()).updateOrderStatus(invalidRequest);
    }

    @SneakyThrows
    @Test
    void updateOrderStatus_shouldReturnForbidden_whenUserHasNoPermission() {
        var progressRequest = getProgressRequest();

        mvc.perform(MockMvcRequestBuilders.put(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(progressRequest)))
                .andExpect(status().isForbidden());

        verify(service, never()).updateOrderStatus(progressRequest);
    }

    @SneakyThrows
    @Test
    void updateOrderStatus_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        var progressRequest = getProgressRequest();

        mvc.perform(MockMvcRequestBuilders.put(API_URI)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(progressRequest)))
                .andExpect(status().isUnauthorized());

        verify(service, never()).updateOrderStatus(progressRequest);
    }

    @SneakyThrows
    @Test
    void findPendingOrdersByTechnicianId_shouldReturnOkAndPageOrderResponse_whenRequested() {
        var pageable = getPageable();
        var orderResponsePage = getOrderResponsePage();
        var orderResponse = getOrderResponse();

        doReturn(orderResponsePage).when(service).findPendingOrdersByTechnicianId(pageable);

        mvc.perform(MockMvcRequestBuilders.get(API_URI + PENDING_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orderResponse.id()))
                .andExpect(jsonPath("$.content[0].clientId").value(orderResponse.clientId()))
                .andExpect(jsonPath("$.content[0].clientName").value(orderResponse.clientName()))
                .andExpect(jsonPath("$.content[0].equipmentId").value(orderResponse.equipmentId()))
                .andExpect(jsonPath("$.content[0].equipmentProblem").value(orderResponse.equipmentProblem()))
                .andExpect(jsonPath("$.content[0].receptionistId").value(orderResponse.receptionistId()))
                .andExpect(jsonPath("$.content[0].technicianId").value(orderResponse.technicianId()))
                .andExpect(jsonPath("$.content[0].status").value(orderResponse.status()))
                .andExpect(jsonPath("$.content[0].creationDate").value(TEST_LOCAL_DATE_TIME));

        verify(service).findPendingOrdersByTechnicianId(pageable);
    }

    @SneakyThrows
    @Test
    void findPendingOrdersByTechnicianId_shouldReturnForbidden_whenUserHasNoPermission() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + PENDING_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

        verify(service, never()).findPendingOrdersByTechnicianId(any());
    }

    @SneakyThrows
    @Test
    void findPendingOrdersByTechnicianId_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + PENDING_ENDPOINT)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

        verify(service, never()).findPendingOrdersByTechnicianId(any());
    }

    @SneakyThrows
    @Test
    void findProgressTrackingByOrderId_shouldReturnOkAndPageTrackingResponse_whenRequested() {
        var pageable = getPageable();
        var trackingResponsePage = getOrderTrackingResponsePage();
        var trackingResponse = getOrderTrackingResponse();

        doReturn(trackingResponsePage).when(orderTrackingService)
                .findProgressTrackingByOrderId(pageable, TEST_ID_ONE);

        mvc.perform(MockMvcRequestBuilders.get(API_URI + String.format(PROGRESS_ENDPOINT, TEST_ID_ONE))
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(trackingResponse.id()))
                .andExpect(jsonPath("$.content[0].orderId").value(trackingResponse.orderId()))
                .andExpect(jsonPath("$.content[0].status").value(trackingResponse.status()))
                .andExpect(jsonPath("$.content[0].progressDetails").value(trackingResponse.progressDetails()))
                .andExpect(jsonPath("$.content[0].progressDate").value(TEST_LOCAL_DATE_TIME));

        verify(orderTrackingService).findProgressTrackingByOrderId(pageable, TEST_ID_ONE);
    }

    @SneakyThrows
    @Test
    void findProgressTrackingByOrderId_shouldReturnForbidden_whenUserHasNoPermission() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + String.format(PROGRESS_ENDPOINT, TEST_ID_ONE))
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

        verify(orderTrackingService, never()).findProgressTrackingByOrderId(any(), any());
    }

    @SneakyThrows
    @Test
    void findProgressTrackingByOrderId_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + String.format(PROGRESS_ENDPOINT, TEST_ID_ONE))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

        verify(orderTrackingService, never()).findProgressTrackingByOrderId(any(), any());
    }

    @SneakyThrows
    @Test
    void getOrderReport_shouldReturnOkAndOrderReportResponsePage_whenRequested() {
        var pageable = getPageable();
        var reportResponsePage = getOrderReportResponsePage();
        var reportResponse = getOrderReportResponse();

        doReturn(reportResponsePage).when(service)
                .getOrderReport(eq(pageable), any());

        mvc.perform(MockMvcRequestBuilders.get(API_URI + REPORT_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE)
                        .param(ID_ATTRIBUTE, valueOf(TEST_ID_ONE))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].orderId").value(reportResponse.orderId()))
                .andExpect(jsonPath("$.content[0].technicianId").value(reportResponse.technicianId()))
                .andExpect(jsonPath("$.content[0].receptionistId").value(reportResponse.receptionistId()))
                .andExpect(jsonPath("$.content[0].clientName").value(reportResponse.clientName()))
                .andExpect(jsonPath("$.content[0].clientEmail").value(reportResponse.clientEmail()))
                .andExpect(jsonPath("$.content[0].clientPhone").value(reportResponse.clientPhone()))
                .andExpect(jsonPath("$.content[0].equipmentType").value(reportResponse.equipmentType()))
                .andExpect(jsonPath("$.content[0].equipmentModel").value(reportResponse.equipmentModel()))
                .andExpect(jsonPath("$.content[0].equipmentProblem").value(reportResponse.equipmentProblem()))
                .andExpect(jsonPath("$.content[0].status").value(reportResponse.status()))
                .andExpect(jsonPath("$.content[0].creationDate").value(TEST_LOCAL_DATE_TIME));

        verify(service).getOrderReport(eq(pageable), any());
    }

    @SneakyThrows
    @Test
    void getOrderReport_shouldReturnForbidden_whenUserHasNoPermission() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + REPORT_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

        verify(service, never()).getOrderReport(any(), any());
    }

    @SneakyThrows
    @Test
    void getOrderReport_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + REPORT_ENDPOINT)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

        verify(service, never()).getOrderReport(any(), any());
    }
}
