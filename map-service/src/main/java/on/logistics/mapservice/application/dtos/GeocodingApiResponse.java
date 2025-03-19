package on.logistics.mapservice.application.dtos;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeocodingApiResponse {

    private String status;
    private Meta meta;
    private List<Address> addresses;
    private String errorMessage;

    @Data
    @NoArgsConstructor
    public static class Meta {

        private Integer totalCount;
        private Integer page;
        private Integer count;
    }

    @Data
    @NoArgsConstructor
    public static class Address {

        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private List<AddressElement> addressElements;
        private String x;
        private String y;
        private Double distance;
    }

    @Data
    @NoArgsConstructor
    public static class AddressElement {

        private List<String> types;
        private String longName;
        private String shortName;
        private String code;
    }

}
