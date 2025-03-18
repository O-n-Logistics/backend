package on.logistics.deliverymanagerservice.domain.entity.repository;

import java.util.Optional;
import java.util.UUID;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryManager;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryManagerRepository {

    DeliveryManager save(DeliveryManager deliveryManager);

    Optional<DeliveryManager> findMaxSequenceByHubIdAndType(UUID hubId, DeliveryType deliveryType);

    Optional<DeliveryManager> findByIdAndIsDeleted(UUID id, boolean isDeleted);
}
