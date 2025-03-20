package on.logistics.hubtransitservice.infrastructure.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import on.logistics.hubtransitservice.domain.entity.HubTransit;
import on.logistics.hubtransitservice.domain.entity.QHubTransit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubTransitQueryRepositoryImpl implements HubTransitQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QHubTransit hubTransit = QHubTransit.hubTransit;

    @Override
    public Page<HubTransit> searchHubTransit(String keyword, Pageable pageable) {
        BooleanExpression keywordCondition = keyword != null && !keyword.isEmpty()
            ? hubTransit.currentHubName.value.containsIgnoreCase(keyword)
            : null;

        List<HubTransit> content = queryFactory.selectFrom(hubTransit)
            .where(keywordCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory.select(hubTransit.count())
            .from(hubTransit)
            .where(keywordCondition)
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}
