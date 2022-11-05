package org.example.Ship.DTO;

import lombok.*;
import org.example.Ship.Entity.Ship;

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
    private List<String> ships;


    public static Function<Collection<Ship>, GetShipsResponse> entityToDtoMapper() {
        return ships -> {
            GetShipsResponse.GetShipsResponseBuilder response = GetShipsResponse.builder();
            ships.stream()
                    .map(Ship::getName)
                    .forEach(response::ship);
            return response.build();
        };
    }
}
