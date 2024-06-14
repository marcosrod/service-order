package com.marcosrod.serviceorder.modules.order.model;

import com.marcosrod.serviceorder.modules.client.model.Client;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import com.marcosrod.serviceorder.modules.order.dto.OrderRequest;
import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "service_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_order_seq")
    @SequenceGenerator(name = "service_order_seq", sequenceName = "service_order_seq", allocationSize = 1)
    private Long id;

    @Column(name = "fk_receptionist")
    private Long receptionistId;

    @Column(name = "fk_technician")
    private Long technicianId;

    @JoinColumn(name = "fk_client")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @JoinColumn(name = "fk_equipment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;

    @Column(name = "equipment_problem")
    private String equipmentProblem;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    public Order(Long receptionistId, Long technicianId, Client client, Equipment equipment,
                 String equipmentProblem, LocalDateTime creationDate, OrderStatus status) {
        this.receptionistId = receptionistId;
        this.technicianId = technicianId;
        this.client = client;
        this.equipment = equipment;
        this.equipmentProblem = equipmentProblem;
        this.creationDate = creationDate;
        this.status = status;
    }

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public static Order of(OrderRequest request, Client client, Equipment equipment) {
        return new Order(request.receptionistId(), request.technicianId(), client, equipment,
                request.equipmentProblem(), LocalDateTime.now(), OrderStatus.P);
    }
}
