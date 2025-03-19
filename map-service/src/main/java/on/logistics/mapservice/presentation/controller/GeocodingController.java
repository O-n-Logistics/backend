package on.logistics.mapservice.presentation.controller;

import lombok.RequiredArgsConstructor;
import on.logistics.mapservice.application.service.GeocodingService;
import on.logistics.mapservice.global.presentation.dtos.CommonResponse;
import on.logistics.mapservice.presentation.dtos.GeocodingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingService geocodingService;

    @GetMapping("/api/v1/maps/geocode")
    public ResponseEntity<CommonResponse<GeocodingResponseDto>> getCoordinates(
        @RequestParam("query") String query
    ) {
        final var responseDto = geocodingService.getCoordinates(query);
        return ResponseEntity.ok().body(CommonResponse.success(responseDto));
    }

}
