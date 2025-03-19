package on.logistics.mapservice.application.service;

import on.logistics.mapservice.presentation.dtos.GeocodingResponseDto;

public interface GeocodingService {

    GeocodingResponseDto getCoordinates(String query);
}
