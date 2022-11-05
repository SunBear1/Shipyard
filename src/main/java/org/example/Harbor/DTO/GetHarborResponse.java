package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Entity.Country;
import org.example.Harbor.Entity.Harbor;
import org.example.Ship.DTO.GetShipsResponse;
import org.example.Ship.Entity.Ship;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetHarborResponse {
    private String name;

    private String code;

    private int capacity;

    private double budget;

    private Country country;

    private GetShipsResponse local_units;

    public static Function<Harbor, GetHarborResponse> entityToDtoMapper(List<Ship> ships) {
        return harbor -> GetHarborResponse.builder()
                .code(harbor.getCode())
                .name(harbor.getName())
                .capacity(harbor.getCapacity())
                .budget(harbor.getBudget())
                .country(harbor.getCountry())
                .local_units(GetShipsResponse.entityToDtoMapper().apply(ships))
                .build();
    }
}
