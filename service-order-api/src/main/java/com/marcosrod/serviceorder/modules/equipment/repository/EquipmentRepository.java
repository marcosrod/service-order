package com.marcosrod.serviceorder.modules.equipment.repository;

import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    boolean existsByTypeAndModel(String type, String model);
}
