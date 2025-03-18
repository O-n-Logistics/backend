package on.logistics.deliverymanagerservice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryManager;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryManagerJpaRepository extends JpaRepository<DeliveryManager, UUID> {

    Optional<DeliveryManager> findMaxSequenceByHubIdAndType(UUID hubId, DeliveryType deliveryType);

    Optional<DeliveryManager> findByIdAndIsDeleted(UUID id, boolean isDeleted);

}
