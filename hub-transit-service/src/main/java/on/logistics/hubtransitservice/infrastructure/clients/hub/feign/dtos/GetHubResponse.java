package on.logistics.hubtransitservice.infrastructure.clients.hub.feign.dtos;

import java.util.UUID;

public record GetHubResponse(
    UUID id,
    String hubName,
    String hubType,
    String address,
    String latitude,
    String longitude
) {

}
