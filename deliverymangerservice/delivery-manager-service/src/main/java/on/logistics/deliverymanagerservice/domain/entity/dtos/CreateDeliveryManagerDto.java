package on.logistics.deliverymanagerservice.domain.entity.dtos;

import java.util.UUID;
import lombok.Builder;
import on.logistics.deliverymanagerservice.application.dtos.CreateDeliveryManagerRequestDto;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;

@Builder
public record CreateDeliveryManagerDto(
    UUID userId,
    UUID hubId,
    DeliveryType deliveryType,
    Integer sequence

) {

    public static CreateDeliveryManagerDto of(CreateDeliveryManagerRequestDto requestDto,
        Integer sequence) {
        return CreateDeliveryManagerDto.builder()
            .userId(requestDto.userId())
            .hubId(requestDto.hubId())
            .deliveryType(requestDto.deliveryType())
            .sequence(sequence)
            .build();
    }
}
