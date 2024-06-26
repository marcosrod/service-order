package com.marcosrod.serviceorder.modules.equipment.service;

import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.equipment.repository.EquipmentRepository;
import com.marcosrod.serviceorder.modules.equipment.service.impl.EquipmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    @InjectMocks
    private EquipmentServiceImpl service;
    @Mock
    private EquipmentRepository repository;

    @Test
    void save_shouldReturnEquipmentResponse_whenValidRequest() {
        var equipmentRequest = getEquipmentRequest();
        var equipmentType = equipmentRequest.type();
        var equipmentModel = equipmentRequest.model();
        var equipmentToSave = getEquipment();

        doReturn(false).when(repository).existsByTypeAndModel(equipmentType, equipmentModel);
        doReturn(getSavedEquipment()).when(repository).save(equipmentToSave);

        assertThat(service.save(equipmentRequest))
                .isEqualTo(getEquipmentResponse());

        verify(repository).existsByTypeAndModel(equipmentType, equipmentModel);
        verify(repository).save(equipmentToSave);
    }

    @Test
    void save_shouldThrowValidationException_whenDuplicatedEquipment() {
        var equipmentRequest = getEquipmentRequest();
        var equipmentType = equipmentRequest.type();
        var equipmentModel = equipmentRequest.model();

        doReturn(true).when(repository).existsByTypeAndModel(equipmentType, equipmentModel);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(equipmentRequest))
                .withMessage(ValidationError.EQUIPMENT_ALREADY_EXISTS.getMessage());

        verify(repository).existsByTypeAndModel(equipmentType, equipmentModel);
        verify(repository, never()).save(any());
    }

    @Test
    void findById_shouldReturnEquipment_whenEquipmentIdExists() {
        var savedEquipment = getSavedEquipment();

        doReturn(Optional.of(savedEquipment)).when(repository).findById(TEST_ID_ONE);

        assertThat(service.findById(TEST_ID_ONE))
                .isEqualTo(savedEquipment);

        verify(repository).findById(TEST_ID_ONE);
    }

    @Test
    void findById_shouldThrowValidationException_whenEquipmentIdDoesNotExists() {
        doReturn(Optional.empty()).when(repository).findById(TEST_ID_ONE);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.findById(TEST_ID_ONE))
                .withMessage(ValidationError.EQUIPMENT_NOT_FOUND.getMessage());

        verify(repository).findById(TEST_ID_ONE);
    }
}