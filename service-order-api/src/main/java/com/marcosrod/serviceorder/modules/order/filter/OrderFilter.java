package com.marcosrod.serviceorder.modules.order.filter;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderFilter {

    private Long id;
    private OrderStatus status;
    private String equipmentType;
    private String equipmentModel;
    private String equipmentProblem;
    private String clientName;
    private String clientEmail;
    private Long technicianId;
    private Long receptionistId;

    public OrderPredicate toPredicate() {
        return new OrderPredicate()
                .withId(id)
                .withStatus(status)
                .withEquipmentType(equipmentType)
                .withEquipmentModel(equipmentModel)
                .withEquipmentProblem(equipmentProblem)
                .withClientName(clientName)
                .withClientEmail(clientEmail)
                .withTechnicianId(technicianId)
                .withReceptionistId(receptionistId);
    }
}
