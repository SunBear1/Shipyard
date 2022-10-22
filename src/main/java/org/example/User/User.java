package org.example.User;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Ship.Ship;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class User implements Serializable {
    private String login;

    private String name;

    private String surname;

    private String email;

    @ToString.Exclude
    private String password;

    private byte[] avatar;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Ship> ships;

}