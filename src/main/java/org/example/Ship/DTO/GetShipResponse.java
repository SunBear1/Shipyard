package org.example.Ship.DTO;

import lombok.*;
import org.example.Ship.Ship;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetShipResponse {
    private Long id;

    private String name;

    private LocalDate CompletionDate;

    private double cost;

    public static Function<Ship, GetShipResponse> entityToDtoMapper() {
        return ship -> GetShipResponse.builder()
                .id(ship.getId())
                .name(ship.getName())
                .CompletionDate(ship.getCompletionDate())
                .cost(ship.getCost())
                .build();
    }
}
