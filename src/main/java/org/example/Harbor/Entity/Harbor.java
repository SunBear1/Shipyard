package org.example.Harbor.Entity;

import lombok.*;
import org.example.Ship.Entity.Ship;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "harbors")
public class Harbor implements Serializable {

    private String name;

    @Id
    private String code;

    private int capacity;

    private double budget;

    private Country country;

    @Transient
    private List<Ship> ships;
}

