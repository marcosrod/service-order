package com.marcosrod.serviceorder.modules.equipment.filter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EquipmentFilter {

    private Long id;
    private String type;
    private String model;

    public EquipmentPredicate toPredicate() {
        return new EquipmentPredicate()
                .withId(id)
                .withType(type)
                .withModel(model);
    }
}
