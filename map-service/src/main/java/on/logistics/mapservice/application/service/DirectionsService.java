package on.logistics.mapservice.application.service;

import on.logistics.mapservice.presentation.dtos.DirectionsResponseDto;

public interface DirectionsService {

    DirectionsResponseDto getRoute(String start, String end, String option, int cartype);
}
