package on.logistics.deliverymanagerservice.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.deliverymanagerservice.application.dtos.CreateDeliveryManagerRequestDto;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryManager;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;
import on.logistics.deliverymanagerservice.domain.entity.dtos.CreateDeliveryManagerDto;
import on.logistics.deliverymanagerservice.domain.entity.repository.DeliveryManagerRepository;
import on.logistics.deliverymanagerservice.presentation.dtos.response.CreateDeliveryManagerResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryManagerService {

    private final DeliveryManagerRepository deliveryManagerRepository;

    @Transactional
    public CreateDeliveryManagerResponse createDeliveryManager(
        CreateDeliveryManagerRequestDto requestDto) {
        Integer sequence = getAssignedSequence(requestDto.hubId(), requestDto.deliveryType());
        CreateDeliveryManagerDto createDeliveryManagerDto = CreateDeliveryManagerDto.of(requestDto,
            sequence);
        DeliveryManager deliveryManager = DeliveryManager.create(createDeliveryManagerDto);
        DeliveryManager savedDeliveryManager = deliveryManagerRepository.save(deliveryManager);
        return CreateDeliveryManagerResponse.of(savedDeliveryManager.getId());
    }

    private Integer getAssignedSequence(UUID hubId, DeliveryType deliveryType) {
        DeliveryManager maxDeliveryManager = deliveryManagerRepository
            .findMaxSequenceByHubIdAndType(hubId, deliveryType)
            .orElse(null);
        int maxSequence = (maxDeliveryManager == null) ? 0 : maxDeliveryManager.getSequence();
        return maxSequence + 1;
    }
}
