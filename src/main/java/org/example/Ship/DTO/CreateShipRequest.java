package org.example.Ship.DTO;

import lombok.*;
import org.example.Harbor.Harbor;
import org.example.Harbor.Service.HarborService;
import org.example.Ship.Ship;
import org.example.User.User;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class CreateShipRequest {

    private static HarborService service;

    private String name;

    private LocalDate completionDate;

    private String harbor_code;

    private double cost;

    public static Function<CreateShipRequest, Ship> dtoToEntityMapper(
            Function<String, Harbor> harborFunction,
            Supplier<User> userSupplier
    ) {
        return request -> Ship.builder()
                .name(request.getName())
                .completionDate(request.getCompletionDate())
                .cost(request.getCost())
                .user(userSupplier.get())
                .harbor(harborFunction.apply(request.getHarbor_code()))
                .build();
    }

}
