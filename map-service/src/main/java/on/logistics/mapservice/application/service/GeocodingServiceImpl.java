package on.logistics.mapservice.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.mapservice.application.dtos.GeocodingApiResponse;
import on.logistics.mapservice.exception.MapException;
import on.logistics.mapservice.presentation.dtos.GeocodingResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j(topic = "Geocoding Service")
@Service
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService {

    private final WebClient webClient;

    @Value("${naver.map.api.url}")
    private String baseUrl;

    @Value("${naver.map.api.key}")
    private String apiKey;

    @Value("${naver.map.api.secret}")
    private String apiSecret;

    @Override
    public GeocodingResponseDto getCoordinates(String query) {
        log.debug("getCoordinates() 호출: query={}", query);
        String url = buildRequestUrl(query);
        try {
            GeocodingApiResponse apiResponse = getGeocodeResponse(url);
            log.info("네이버 geocode API 호출 성공: query={}, 응답코드={}", query, apiResponse.getStatus());
            GeocodingApiResponse.Address address = extractAddress(apiResponse);
            return GeocodingResponseDto.from(address);
        } catch (MapException.InvalidResponseException | MapException.ExternalApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new MapException.ExternalApiCallException();
        }
    }

    private String buildRequestUrl(String query) {
        String requestUrl = baseUrl + "/map-geocode/v2/geocode?query=" + query;
        log.debug("buildRequestUrl(): {}", requestUrl);
        return requestUrl;
    }

    private GeocodingApiResponse getGeocodeResponse(String url) {
        return webClient.get()
            .uri(url)
            .header("x-ncp-apigw-api-key-id", apiKey)
            .header("x-ncp-apigw-api-key", apiSecret)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GeocodingApiResponse.class)
            .block();
    }

    private GeocodingApiResponse.Address extractAddress(GeocodingApiResponse response) {
        if (response == null || response.getAddresses() == null || response.getAddresses()
            .isEmpty()) {
            throw new MapException.InvalidResponseException();
        }
        return response.getAddresses().get(0);
    }

}
