package com.marcosrod.serviceorder.modules.order.filter;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import com.querydsl.core.BooleanBuilder;

import static com.marcosrod.serviceorder.modules.order.model.QOrder.order;

public class OrderPredicate {

    protected BooleanBuilder builder;

    public OrderPredicate() {
        this.builder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.builder;
    }

    public OrderPredicate withId(Long id) {
        if (id != null) {
            builder.and(order.id.eq(id));
        }
        return this;
    }

    public OrderPredicate withStatus(OrderStatus status) {
        if (status != null) {
            builder.and(order.status.eq(status));
        }
        return this;
    }

    public OrderPredicate withEquipmentType(String equipmentType) {
        if (equipmentType != null && !equipmentType.isBlank()) {
            builder.and(order.equipment.type.eq(equipmentType));
        }
        return this;
    }

    public OrderPredicate withEquipmentModel(String equipmentModel) {
        if (equipmentModel != null && !equipmentModel.isBlank()) {
            builder.and(order.equipment.model.eq(equipmentModel));
        }
        return this;
    }

    public OrderPredicate withEquipmentProblem(String equipmentProblem) {
        if (equipmentProblem != null && !equipmentProblem.isBlank()) {
            builder.and(order.equipmentProblem.eq(equipmentProblem));
        }
        return this;
    }

    public OrderPredicate withClientName(String clientName) {
        if (clientName != null && !clientName.isBlank()) {
            builder.and(order.client.name.eq(clientName));
        }
        return this;
    }

    public OrderPredicate withClientEmail(String clientEmail) {
        if (clientEmail != null && !clientEmail.isBlank()) {
            builder.and(order.client.email.eq(clientEmail));
        }
        return this;
    }

    public OrderPredicate withTechnicianId(Long technicianId) {
        if (technicianId != null) {
            builder.and(order.technicianId.eq(technicianId));
        }
        return this;
    }

    public OrderPredicate withReceptionistId(Long receptionistId) {
        if (receptionistId != null) {
            builder.and(order.receptionistId.eq(receptionistId));
        }
        return this;
    }
}
