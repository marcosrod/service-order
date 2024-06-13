package com.marcosrod.serviceorder.modules.equipment.model;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_seq")
    @SequenceGenerator(name = "equipment_seq", sequenceName = "equipment_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "model")
    private String model;

    public Equipment(String type, String model) {
        this.type = type;
        this.model = model;
    }

    public static Equipment of(EquipmentRequest request) {
        return new Equipment(request.model(), request.type());
    }
}
