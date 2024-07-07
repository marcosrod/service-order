package com.marcosrod.serviceorder.modules.equipment.repository;

import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>,
        QuerydslPredicateExecutor<Equipment> {
    boolean existsByTypeAndModel(String type, String model);
}
