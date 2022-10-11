package org.example.Ship;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Harbor.Harbor;
import org.example.User.User;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Ship {

    String name;

    LocalDate ship_completion_date;

    User user;

    Harbor harbor;

    double cost;
}
