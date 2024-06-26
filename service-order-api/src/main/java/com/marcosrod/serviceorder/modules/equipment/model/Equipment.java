package com.marcosrod.serviceorder.modules.equipment.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
