package org.example.Harbor;

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
public class Harbor {
    Long id;

    String name;

    String domain;

    int capacity;

    double budget;

    Country country;

    private List<Ship> ships;
}

enum Country{
    Poland, Dutch, Spain, England, Italy, Czech, German
}