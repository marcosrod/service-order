package com.marcosrod.serviceorder.modules.order.controller.contract;

import com.marcosrod.serviceorder.modules.order.dto.*;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
public interface IOrderController {

    @Operation(summary = "Save new Order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Order saved."),
            @ApiResponse(responseCode = "500", description = "One or more requested users doesn't exists."),
            @ApiResponse(responseCode = "500",
                    description = "There's already an open Order for this same Client and Equipment."),
            @ApiResponse(responseCode = "500", description = "This client doesn't exists"),
            @ApiResponse(responseCode = "500", description = "This equipment doesn't exists."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @PostMapping
    OrderResponse save(@RequestBody @Valid OrderRequest request);

    @Operation(summary = "Update an existent Order's Status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order's Status updated."),
            @ApiResponse(responseCode = "500", description = "This order doesn't exists."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @PutMapping
    OrderResponse updateOrderStatus(@RequestBody @Valid OrderProgressRequest request);

    @Operation(summary = "Find Orders by Technician Id.", description = "Find the pending orders by Technician.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns page containing the pending orders."),
            @ApiResponse(responseCode = "200", description = "Returns empty page."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping("pending")
    Page<OrderResponse> findPendingOrdersByTechnicianId(Pageable pageable);

    @Operation(summary = "Find the Order's progress.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns page containing the order's progress tracking."),
            @ApiResponse(responseCode = "200", description = "Returns empty page."),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping("{id}/progress")
    Page<OrderTrackingResponse> findProgressTrackingByOrderId(Pageable pageable, @PathVariable Long id);

    @Operation(summary = "Get the orders' report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns page containing the order report."),
            @ApiResponse(responseCode = "200", description = "Returns empty page."),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping("report")
    Page<OrderReportResponse> getOrderReport(Pageable pageable, OrderFilter filter);
}
