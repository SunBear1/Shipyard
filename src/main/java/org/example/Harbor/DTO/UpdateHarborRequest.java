package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Entity.Country;
import org.example.Harbor.Entity.Harbor;
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
public class UpdateHarborRequest {
    private String name;

    private int capacity;

    private double budget;

    private Country country;


    public static Function<UpdateHarborRequest, Harbor> dtoToEntityMapper(String code, List<Ship> ships) {
        return request -> Harbor.builder()
                .name(request.getName())
                .budget(request.getBudget())
                .country(request.getCountry())
                .capacity(request.getCapacity())
                .ships(ships)
                .code(code)
                .build();
    }

}
