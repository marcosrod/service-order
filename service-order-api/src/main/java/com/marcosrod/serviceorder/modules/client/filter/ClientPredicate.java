package com.marcosrod.serviceorder.modules.client.filter;

import com.querydsl.core.BooleanBuilder;

import static com.marcosrod.serviceorder.modules.client.model.QClient.client;

public class ClientPredicate {

    protected BooleanBuilder builder;

    public ClientPredicate() {
        this.builder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.builder;
    }

    public ClientPredicate withId(Long id) {
        if (id != null) {
            builder.and(client.id.eq(id));
        }
        return this;
    }

    public ClientPredicate withName(String name) {
        if (name != null && !name.isBlank()) {
            builder.and(client.name.eq(name));
        }
        return this;
    }

    public ClientPredicate withAddress(String address) {
        if (address != null && !address.isBlank()) {
            builder.and(client.address.eq(address));
        }
        return this;
    }

    public ClientPredicate withPhone(String phone) {
        if (phone != null && !phone.isBlank()) {
            builder.and(client.phone.eq(phone));
        }
        return this;
    }

    public ClientPredicate withEmail(String email) {
        if (email != null && !email.isBlank()) {
            builder.and(client.email.eq(email));
        }
        return this;
    }
}
