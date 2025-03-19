package on.logistics.mapservice.application.dtos;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class DirectionsApiResponse {

    private Integer code;
    private String message;
    private String currentDateTime;
    private RouteWrapper route;

    @Data
    @NoArgsConstructor
    public static class RouteWrapper {

        private List<RouteInfo> routeInfo;

        @JsonAnySetter
        public void setDynamicRoute(String key, List<RouteInfo> value) {
            if (this.routeInfo == null) {
                this.routeInfo = value;
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class RouteInfo {

        private Summary summary;
    }

    @Data
    @NoArgsConstructor
    public static class Summary {

        private Start start;
        private Goal goal;
        private Integer distance;
        private Long duration;
        private String departureTime;
        private Integer tollFare;
    }

    @Data
    @NoArgsConstructor
    public static class Start {

        private List<Double> location;
    }

    @Data
    @NoArgsConstructor
    public static class Goal {

        private List<Double> location;
        private Integer dir;
    }

}
