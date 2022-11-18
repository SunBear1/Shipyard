package org.example.Harbor.Entity;

import lombok.*;
import org.example.Ship.Entity.Ship;

import javax.persistence.*;
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "harbor", cascade = CascadeType.REMOVE)
    private List<Ship> ships;
}

