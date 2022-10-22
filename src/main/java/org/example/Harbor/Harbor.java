package org.example.Harbor;

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
public class Harbor implements Serializable {

    private String name;

    private String code;

    private int capacity;

    private double budget;

    private Country country;

    private List<Ship> ships;
}

