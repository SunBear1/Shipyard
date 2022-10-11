package org.example.User;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Ship.Ship;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class User {
    private Long id;

    private String name;

    @ToString.Exclude
    private String password;

    private Byte userPhoto;

    private List<Ship> ships;

}