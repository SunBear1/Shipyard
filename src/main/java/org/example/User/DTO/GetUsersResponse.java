package org.example.User.DTO;

import lombok.*;
import org.example.User.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetUsersResponse {

    @Singular
    private List<String> users;

    public static Function<Collection<User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream().map(User::getLogin).forEach(response::user);
            return response.build();
        };

    }
}
