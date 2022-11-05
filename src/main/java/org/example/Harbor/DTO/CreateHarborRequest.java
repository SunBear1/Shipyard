package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Entity.Country;
import org.example.Harbor.Entity.Harbor;
import org.example.Ship.Entity.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateHarborRequest {
    private String name;

    private String code;

    private int capacity;

    private double budget;

    private Country country;

    private List<Ship> ships;

    public static Function<CreateHarborRequest, Harbor> dtoToEntityMapper() {
        return request -> Harbor.builder()
                .name(request.getName())
                .code(request.getCode())
                .budget(request.getBudget())
                .country(request.getCountry())
                .capacity(request.getCapacity())
                .ships(new ArrayList<>())
                .build();
    }

}
