package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Country;
import org.example.Harbor.Harbor;
import org.example.Ship.Ship;

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

    private List<Ship> ships;


    public static Function<Harbor, GetHarborResponse> entityToDtoMapper() {
        return harbor -> GetHarborResponse.builder()
                .code(harbor.getCode())
                .name(harbor.getName())
                .capacity(harbor.getCapacity())
                .budget(harbor.getBudget())
                .country(harbor.getCountry())
                .ships(harbor.getShips())
                .build();
    }
}
