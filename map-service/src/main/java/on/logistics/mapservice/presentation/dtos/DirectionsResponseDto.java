package on.logistics.mapservice.presentation.dtos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import on.logistics.mapservice.application.dtos.DirectionsApiResponse;

@Builder
public record DirectionsResponseDto(
    RouteSummary summary
) {

    public static DirectionsResponseDto fromRouteInfo(DirectionsApiResponse.RouteInfo routeInfo) {
        DirectionsApiResponse.Summary summary = routeInfo.getSummary();
        LocalDateTime departure = LocalDateTime.parse(summary.getDepartureTime(),
            DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime eta = departure.plus(Duration.ofMillis(summary.getDuration()));

        RouteSummary routeSummary = RouteSummary.builder()
            .distance(summary.getDistance())
            .duration(summary.getDuration())
            .start(Start.builder()
                .location(summary.getStart().getLocation())
                .build())
            .end(End.builder()
                .location(summary.getGoal().getLocation())
                .build())
            .departureTime(summary.getDepartureTime())
            .eta(eta.format(DateTimeFormatter.ISO_DATE_TIME))
            .tollFare(summary.getTollFare())
            .build();

        return DirectionsResponseDto.builder()
            .summary(routeSummary)
            .build();
    }

    @Builder
    public record RouteSummary(
        Integer distance,
        Long duration,
        Start start,
        End end,
        String departureTime,
        String eta,
        Integer tollFare
    ) {

    }

    @Builder
    public record Start(
        List<Double> location
    ) {

    }

    @Builder
    public record End(
        List<Double> location
    ) {

    }

}