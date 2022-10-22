package org.example.User.DTO;

import lombok.*;
import org.example.User.User;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetUserResponse {
    private String login;

    private String name;

    private String surname;

    private String email;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();
    }


}
