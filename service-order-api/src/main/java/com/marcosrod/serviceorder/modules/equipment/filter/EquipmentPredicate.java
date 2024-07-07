package com.marcosrod.serviceorder.modules.equipment.filter;

import com.querydsl.core.BooleanBuilder;

import static com.marcosrod.serviceorder.modules.equipment.model.QEquipment.equipment;
import static com.marcosrod.serviceorder.modules.order.model.QOrder.order;

public class EquipmentPredicate {

    protected BooleanBuilder builder;

    public EquipmentPredicate() {
        this.builder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.builder;
    }

    public EquipmentPredicate withId(Long id) {
        if (id != null) {
            builder.and(order.id.eq(id));
        }
        return this;
    }

    public EquipmentPredicate withType(String type) {
        if (type != null && !type.isBlank()) {
            builder.and(equipment.type.eq(type));
        }
        return this;
    }

    public EquipmentPredicate withModel(String model) {
        if (model != null && !model.isBlank()) {
            builder.and(equipment.model.eq(model));
        }
        return this;
    }
}
