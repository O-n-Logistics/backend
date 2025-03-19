package on.logistics.mapservice.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.mapservice.application.dtos.DirectionsApiResponse;
import on.logistics.mapservice.application.dtos.DirectionsApiResponse.RouteInfo;
import on.logistics.mapservice.exception.MapException;
import on.logistics.mapservice.presentation.dtos.DirectionsResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j(topic = "Directions Service")
@Service
@RequiredArgsConstructor
public class DirectionsServiceImpl implements DirectionsService {

    private final WebClient webClient;

    @Value("${naver.map.api.url}")
    private String baseUrl;

    @Value("${naver.map.api.key}")
    private String apiKey;

    @Value("${naver.map.api.secret}")
    private String apiSecret;

    @Override
    public DirectionsResponseDto getRoute(String start, String end, String option, int cartype) {
        log.debug("getRoute() 호출: start={}, end={}, option={}, cartype={}", start, end, option,
            cartype);
        String url = buildRequestUrl(start, end, option, cartype);
        try {
            DirectionsApiResponse directionsApiResponse = getDirectionResponse(url);
            if (!hasValidRouteInfo(directionsApiResponse)) {
                throw new MapException.InvalidResponseException();
            }
            log.info("경로 API 호출 성공: url={}, 응답코드={}", url, directionsApiResponse.getCode());
            return DirectionsResponseDto.fromRouteInfo(getRouteInfo(directionsApiResponse));
        } catch (MapException.InvalidResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new MapException.ExternalApiCallException();
        }
    }

    private String buildRequestUrl(String start, String end, String option, int cartype) {
        String requestUrl = String.format(
            "%s/map-direction/v1/driving?start=%s&goal=%s&option=%s&cartype=%d", baseUrl, start,
            end, option, cartype);
        log.debug("buildRequestUrl(): {}", requestUrl);
        return requestUrl;
    }

    private DirectionsApiResponse getDirectionResponse(String url) {
        return webClient.get()
            .uri(url)
            .header("x-ncp-apigw-api-key-id", apiKey)
            .header("x-ncp-apigw-api-key", apiSecret)
            .retrieve()
            .bodyToMono(DirectionsApiResponse.class)
            .block();
    }

    private boolean hasValidRouteInfo(DirectionsApiResponse apiResponse) {
        return apiResponse != null &&
            apiResponse.getRoute() != null &&
            apiResponse.getRoute().getRouteInfo() != null &&
            !apiResponse.getRoute().getRouteInfo().isEmpty();
    }

    private RouteInfo getRouteInfo(DirectionsApiResponse directionsApiResponse) {
        return directionsApiResponse.getRoute().getRouteInfo().get(0);
    }

}
