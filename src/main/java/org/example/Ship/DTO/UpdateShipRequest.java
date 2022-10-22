package org.example.Ship.DTO;

import lombok.*;
import org.example.Ship.Ship;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateShipRequest {
    private String name;

    private double cost;

    private LocalDate completionDate;

    public static BiFunction<Ship, UpdateShipRequest, Ship> dtoToEntityUpdater() {
        return (ship, request) -> {
            ship.setName(request.getName());
            ship.setCost(request.getCost());
            ship.setCompletionDate(request.getCompletionDate());
            return ship;
        };
    }

}
