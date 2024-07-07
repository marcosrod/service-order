package com.marcosrod.authentication.modules.user.helper;

import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.enums.Role;
import com.marcosrod.authentication.modules.user.filter.UserFilter;
import com.marcosrod.authentication.modules.user.filter.UserPredicate;
import com.marcosrod.authentication.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.authentication.modules.common.helper.TestHelper.getPageable;

public class UserHelper {

    private static final String TEST_NAME = "testName";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "testPass";

    public static UserRequest getUserRequest(Role role) {
        return new UserRequest(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, role);
    }

    public static UserResponse getUserResponse(Role role) {
        return new UserResponse(TEST_ID_ONE, TEST_NAME, TEST_EMAIL, role.getDescription());
    }

    public static User getUser(Role role) {
        return new User(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, role);
    }

    public static User getSavedUser(Role role) {
        return new User(TEST_ID_ONE, TEST_NAME, TEST_EMAIL, TEST_PASSWORD, role);
    }

    public static Page<UserResponse> getUserResponsePage(Role role) {
        return new PageImpl<>(Collections.singletonList(getUserResponse(role)),
                getPageable(), TEST_ID_ONE);
    }

    public static UserFilter getUserFilter() {
        var filter = new UserFilter();
        filter.setId(TEST_ID_ONE);

        return filter;
    }

    public static UserFilter getUserAllParametersFilter() {
        var savedUser = getSavedUser(Role.R);

        return new UserFilter(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }

    public static UserPredicate getUserAllParametersPredicate() {
        var savedUser = getSavedUser(Role.R);

        return new UserPredicate()
                .withId(savedUser.getId())
                .withName(savedUser.getName())
                .withEmail(savedUser.getEmail())
                .withRole(savedUser.getRole());
    }
}
