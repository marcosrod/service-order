package com.marcosrod.authentication.modules.user.service;

import com.marcosrod.authentication.modules.common.enums.ValidationError;
import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.user.model.User;
import com.marcosrod.authentication.modules.user.repository.UserRepository;
import com.marcosrod.authentication.modules.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.TEST_ID_TWO;
import static com.marcosrod.authentication.modules.common.helper.TestHelper.getPageable;
import static com.marcosrod.authentication.modules.user.enums.Role.R;
import static com.marcosrod.authentication.modules.user.enums.Role.T;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void getAll_shouldReturnPageUserResponse_whenRequested() {
        var savedUser = getSavedUser(R);
        var pageable = getPageable();
        var filter = getUserFilter();
        var predicate = filter.toPredicate().build();
        Page<User> usersPage = new PageImpl<>(Collections.singletonList(savedUser), pageable, TEST_ID_ONE);

        doReturn(usersPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getAll(pageable, filter))
                .isEqualTo(getUserResponsePage(R));

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void getAll_shouldReturnEmptyPage_whenNoClientsFound() {
        var pageable = getPageable();
        var filter = getUserFilter();
        var predicate = filter.toPredicate().build();
        Page<User> emptyPage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);

        doReturn(emptyPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getAll(pageable, filter))
                .isEmpty();

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void save_shouldReturnUserResponse_whenReceptionistUserRequested() {
        var userToSave = getUser(R);
        var userRequest = getUserRequest(R);
        var userEmail = userRequest.email();
        var userPassword = userRequest.password();

        doReturn(false).when(repository).existsByEmail(userEmail);
        doReturn(getSavedUser(R)).when(repository).save(userToSave);
        doReturn(userPassword).when(passwordEncoder).encode(userPassword);

        assertThat(service.save(userRequest))
                .isEqualTo(getUserResponse(R));

        verify(repository).existsByEmail(userEmail);
        verify(repository).save(userToSave);
        verify(passwordEncoder).encode(userPassword);
    }

    @Test
    void save_shouldReturnUserResponse_whenTechnicianUserRequested() {
        var userToSave = getUser(T);
        var userRequest = getUserRequest(T);
        var userEmail = userRequest.email();
        var userPassword = userRequest.password();

        doReturn(false).when(repository).existsByEmail(userEmail);
        doReturn(getSavedUser(T)).when(repository).save(userToSave);
        doReturn(userPassword).when(passwordEncoder).encode(userPassword);

        assertThat(service.save(userRequest))
                .isEqualTo(getUserResponse(T));

        verify(repository).existsByEmail(userEmail);
        verify(repository).save(userToSave);
        verify(passwordEncoder).encode(userPassword);
    }

    @Test
    void save_shouldThrowValidationException_whenDuplicatedUserEmail() {
        var userRequest = getUserRequest(R);
        var userEmail = userRequest.email();

        doReturn(true).when(repository).existsByEmail(userEmail);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(userRequest))
                .withMessage(ValidationError.USER_EMAIL_ALREADY_EXISTS.getMessage());

        verify(repository).existsByEmail(userEmail);
        verify(repository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void findUsersById_shouldReturnTrue_whenUsersExists() {
        var idsList = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(List.of(getSavedUser(R), getSavedUser(T))).when(repository).findAllById(idsList);

        assertTrue(service.findUsersById(idsList));

        verify(repository).findAllById(idsList);
    }

    @Test
    void findUsersById_shouldReturnFalse_whenUsersDoesNotExists() {
        var idsList = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(List.of(getSavedUser(R))).when(repository).findAllById(idsList);

        assertFalse(service.findUsersById(idsList));

        verify(repository).findAllById(idsList);
    }

    @Test
    void findByEmail_shouldReturnUser_whenValidUserEmail() {
        var savedUser = getSavedUser(R);
        var userEmail = savedUser.getEmail();

        doReturn(Optional.of(savedUser)).when(repository).findByEmail(userEmail);

        assertThat(service.findByEmail(userEmail))
                .isEqualTo(savedUser);

        verify(repository).findByEmail(userEmail);
    }

    @Test
    void findByEmail_shouldThrowValidationException_whenInvalidUserEmail() {
        var userEmail = getSavedUser(R).getEmail();

        doReturn(Optional.empty()).when(repository).findByEmail(userEmail);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.findByEmail(userEmail))
                .withMessage(ValidationError.USER_EMAIL_NOT_FOUND.getMessage());

        verify(repository).findByEmail(userEmail);
    }

}
