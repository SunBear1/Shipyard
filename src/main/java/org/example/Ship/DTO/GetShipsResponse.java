package org.example.Ship.DTO;

import lombok.*;
import org.example.Ship.Ship;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetShipsResponse {

    private Long id;

    private String name;

    @Singular
    private List<Ship> ships;

    public static Function<Collection<Ship>, GetShipsResponse> entityToDtoMapper() {
        return ships -> {
            GetShipsResponse.GetShipsResponseBuilder response = GetShipsResponse.builder();
            ships.stream()
                    .map(ship -> Ship.builder()
                            .id(ship.getId())
                            .name(ship.getName())
                            .build())
                    .forEach(response::ship);
            return response.build();
        };

    }
}
