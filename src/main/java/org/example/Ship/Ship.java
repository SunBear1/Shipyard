package org.example.Ship;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Harbor.Harbor;
import org.example.User.User;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Ship implements Serializable {
    private Long id;

    private String name;

    private LocalDate completionDate;

    private User user;

    private Harbor harbor;

    private double cost;
}
