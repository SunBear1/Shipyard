package org.example.Ship.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.Harbor.Entity.Harbor;
import org.example.User.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ships")
public class Ship implements Serializable {

    @Id
    private Long id;

    private String name;

    private LocalDate completionDate;

    private User user;

    @ManyToOne
    @JoinColumn(name = "harbor")
    private Harbor harbor;

    private double cost;
}
