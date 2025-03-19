package on.logistics.mapservice.presentation.dtos;

import lombok.Builder;
import on.logistics.mapservice.application.dtos.GeocodingApiResponse;

@Builder
public record GeocodingResponseDto(
    String roadAddress,
    String jibunAddress,
    String longitude,
    String latitude
) {

    public static GeocodingResponseDto from(GeocodingApiResponse.Address address) {
        return GeocodingResponseDto.builder()
            .roadAddress(address.getRoadAddress())
            .jibunAddress(address.getJibunAddress())
            .longitude(address.getX())
            .latitude(address.getY())
            .build();
    }

}