package com.marcosrod.serviceorder.modules.order.model;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "service_order_tracking")
public class OrderTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_order_tracking_seq")
    @SequenceGenerator(name = "service_order_tracking_seq", sequenceName = "service_order_tracking_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_service_order")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "progress_details")
    private String progressDetails;

    @Column(name = "progress_date")
    private LocalDateTime progressDate;

    public OrderTracking(Order order, OrderStatus status, String progressDetails, LocalDateTime progressDate) {
        this.order = order;
        this.status = status;
        this.progressDetails = progressDetails;
        this.progressDate = progressDate;
    }

    public static OrderTracking of(Order order, String progressDetails) {
        return new OrderTracking(order, order.getStatus(), progressDetails, LocalDateTime.now());
    }
}
