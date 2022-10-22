package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Harbor;
import org.example.Ship.DTO.GetShipsResponse;
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
public class GetHarborsResponse {
    private String code;

    private String name;

    @Singular
    private List<Harbor> harbors;

    public static Function<Collection<Harbor>, GetHarborsResponse> entityToDtoMapper() {
        return harbors -> {
            GetHarborsResponse.GetHarborsResponseBuilder response = GetHarborsResponse.builder();
            harbors.stream()
                    .map(harbor -> Harbor.builder()
                            .code(harbor.getCode())
                            .name(harbor.getName())
                            .build())
                    .forEach(response::harbor);
            return response.build();
        };

    }
}
