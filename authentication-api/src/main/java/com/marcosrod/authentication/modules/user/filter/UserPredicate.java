package com.marcosrod.authentication.modules.user.filter;

import com.marcosrod.authentication.modules.user.enums.Role;
import com.querydsl.core.BooleanBuilder;

import static com.marcosrod.authentication.modules.user.model.QUser.user;

public class UserPredicate {

    protected BooleanBuilder builder;

    public UserPredicate() {
        this.builder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.builder;
    }

    public UserPredicate withId(Long id) {
        if (id != null) {
            builder.and(user.id.eq(id));
        }
        return this;
    }

    public UserPredicate withName(String name) {
        if (name != null && !name.isBlank()) {
            builder.and(user.name.eq(name));
        }
        return this;
    }

    public UserPredicate withEmail(String email) {
        if (email != null && !email.isBlank()) {
            builder.and(user.email.eq(email));
        }
        return this;
    }

    public UserPredicate withRole(Role role) {
        if (role != null) {
            builder.and(user.role.eq(role));
        }
        return this;
    }
}
