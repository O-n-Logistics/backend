package on.logistics.hubtransitservice.infrastructure.querydsl;

import on.logistics.hubtransitservice.domain.entity.HubTransit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface HubTransitQueryRepository {

    Page<HubTransit> searchHubTransit(String keyword, Pageable pageable);

}
