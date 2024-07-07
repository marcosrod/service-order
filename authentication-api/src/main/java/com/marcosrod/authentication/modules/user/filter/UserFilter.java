package com.marcosrod.authentication.modules.user.filter;

import com.marcosrod.authentication.modules.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserFilter {

    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserPredicate toPredicate() {
        return new UserPredicate()
                .withId(id)
                .withName(name)
                .withEmail(email)
                .withRole(role);
    }
}
