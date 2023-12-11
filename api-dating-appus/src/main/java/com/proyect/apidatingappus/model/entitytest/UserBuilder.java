package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.model.AccessPermits;
import com.proyect.apidatingappus.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBuilder {
    private Long id;
    private String name;
    private String email;
    private String password;
    private AccessPermits accessPermits;

    private UserBuilder toUserBuilder() {
        return UserBuilder.builder()
                .id(1L)
                .name("Gabriel Lima")
                .email("gabriel@gabriel.com")
                .password("123456789")
                .accessPermits(null)
                .build();
    }

    public User toUser() {
        UserBuilder builder = toUserBuilder();
        return new User(builder.id, builder.name, builder.email, builder.password, builder.accessPermits);
    }

    public User toEditPassword(String password) {
        UserBuilder builder = toUserBuilder();
        return new User(builder.id, builder.name, builder.email, password, builder.accessPermits);
    }
}
