package org.example.Ship.DTO;

import lombok.*;
import org.example.Harbor.Harbor;
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
    private String name;

    private LocalDate completionDate;

    private User user;

    private String harbor;

    private double cost;

    public static Function<CreateShipRequest, Ship> dtoToEntityMapper(
            Function<String, Harbor> harborFunction,
            Supplier<User> userSupplier) {
        return request -> Ship.builder()
                .name(request.getName())
                .completionDate(request.getCompletionDate())
                .cost(request.getCost())
                .harbor(harborFunction.apply(request.getHarbor()))
                .user(userSupplier.get())
                .build();
    }

}
