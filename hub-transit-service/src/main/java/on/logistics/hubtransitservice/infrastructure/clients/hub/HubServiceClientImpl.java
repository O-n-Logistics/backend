package on.logistics.hubtransitservice.infrastructure.clients.hub;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.hubtransitservice.global.presentation.dtos.CommonResponse;
import on.logistics.hubtransitservice.global.presentation.dtos.PageDto;
import on.logistics.hubtransitservice.global.utils.FeignClientResponseUtils;
import on.logistics.hubtransitservice.infrastructure.clients.exception.ExternalApiException;
import on.logistics.hubtransitservice.infrastructure.clients.exception.ExternalApiExceptionCode;
import on.logistics.hubtransitservice.infrastructure.clients.hub.feign.HubServiceFeignClient;
import on.logistics.hubtransitservice.infrastructure.clients.hub.feign.dtos.GetHubResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubServiceClientImpl implements HubServiceClient {

    private final HubServiceFeignClient hubServiceFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public GetHubResponse getHubById(UUID hubId) {
        log.info("허브 조회 요청, hubId: {}", hubId);
        Response response = hubServiceFeignClient.getHubById(hubId);
        return FeignClientResponseUtils.getBody(response, GetHubResponse.class);
    }

    @Override
    public GetHubResponse getHubByName(String hubName) {
        log.info("허브 전체 조회 요청, filtering by hubName: {}", hubName);
        Response response = hubServiceFeignClient.getAllHubs();
        try (InputStream inputStream = response.body().asInputStream()) {
            CommonResponse<PageDto<GetHubResponse>> commonResponse = objectMapper.readValue(
                inputStream, new TypeReference<>() {
                }
            );
            PageDto<GetHubResponse> pageDto = commonResponse.data();
            if (pageDto != null) {
                return pageDto.content().stream()
                    .filter(hub -> hub.name().equals(hubName))
                    .findFirst()
                    .orElse(null);
            }
        } catch (IOException e) {
            throw new ExternalApiException(ExternalApiExceptionCode.HUB_PARSING_ERROR);
        }
        return null;
    }

}
