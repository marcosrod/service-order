package com.marcosrod.serviceorder.modules.client.filter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ClientFilter {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public ClientPredicate toPredicate() {
        return new ClientPredicate()
                .withId(id)
                .withName(name)
                .withAddress(address)
                .withPhone(phone)
                .withEmail(email);
    }
}
