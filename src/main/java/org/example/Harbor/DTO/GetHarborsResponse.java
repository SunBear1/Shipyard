package org.example.Harbor.DTO;

import lombok.*;
import org.example.Harbor.Entity.Harbor;

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

    @Singular
    private List<String> harbors;

    public static Function<Collection<Harbor>, GetHarborsResponse> entityToDtoMapper() {
        return harbors -> {
            GetHarborsResponse.GetHarborsResponseBuilder response = GetHarborsResponse.builder();
            harbors.stream()
                    .map(Harbor::getName)
                    .forEach(response::harbor);
            return response.build();
        };
    }
}
