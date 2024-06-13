package com.marcosrod.serviceorder.modules.client.model;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public Client(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public static Client of(ClientRequest request) {
        return new Client(request.name(), request.address(), request.phone(), request.email());
    }
}
