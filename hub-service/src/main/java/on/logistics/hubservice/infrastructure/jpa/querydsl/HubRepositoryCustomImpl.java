package on.logistics.hubservice.infrastructure.jpa.querydsl;

import static on.logistics.hubservice.domain.entity.QCenterSpokeHubLink.centerSpokeHubLink;
import static on.logistics.hubservice.domain.entity.QHub.hub;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.domain.entity.HubType;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.global.enums.PageSortBy;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.QGetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.QSearchHubResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class HubRepositoryCustomImpl implements HubRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageDto<SearchHubResponse> searchHub(SearchHubRequestDto requestDto) {
        List<SearchHubResponse> content = getHubs(requestDto);
        boolean last = true;
        long totalElement = getTotalElement(requestDto);
        int totalPages = getTotalPages(totalElement, requestDto);

        return new PageDto<>(content, last, totalPages, totalElement);
    }

    @Override
    public List<GetSpokesLinkedToCenterResponse> getSpokesLinkedToCenter(UUID centerId) {
        return jpaQueryFactory.select(new QGetSpokesLinkedToCenterResponse(
                centerSpokeHubLink.spoke.id,
                centerSpokeHubLink.spoke.name.value,
                centerSpokeHubLink.spoke.type.stringValue(),
                centerSpokeHubLink.spoke.address.value,
                centerSpokeHubLink.spoke.latitude,
                centerSpokeHubLink.spoke.longitude
            ))
            .from(centerSpokeHubLink)
            .join(hub).on(centerSpokeHubLink.spoke.id.eq(hub.id))
            .where(centerSpokeHubLink.center.id.eq(centerId))
            .fetch();
    }

    private List<SearchHubResponse> getHubs(SearchHubRequestDto requestDto) {
        return jpaQueryFactory.select(new QSearchHubResponse(
                hub.id,
                hub.name.value,
                hub.type.stringValue(),
                hub.address.value,
                hub.latitude,
                hub.longitude
            ))
            .from(hub)
            .where(
                keywordContains(requestDto.keyword()),
                typeEquals(requestDto.type()),
                hub.isDeleted.isFalse()
            )
            .orderBy(getOrderConditions(requestDto.pageable().getSort()))
            .offset(requestDto.pageable().getOffset())
            .limit(requestDto.pageable().getPageSize())
            .fetch();
    }

    private int getTotalPages(long totalElement, SearchHubRequestDto requestDto) {
        return (int) Math.ceil((double) totalElement / requestDto.pageable().getPageSize());
    }

    private long getTotalElement(SearchHubRequestDto requestDto) {
        return Optional.ofNullable(jpaQueryFactory.select(hub.count())
                .from(hub)
                .where(
                    keywordContains(requestDto.keyword()),
                    typeEquals(requestDto.type()),
                    hub.isDeleted.isFalse()
                )
                .fetchOne())
            .orElse(0L);
    }

    private OrderSpecifier<?>[] getOrderConditions(Sort sort) {
        return sort.stream()
            .map(order -> {
                String sortBy = order.getProperty();
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                return switch (PageSortBy.valueOf(sortBy.toUpperCase())) {
                    case CREATED_AT -> new OrderSpecifier<>(direction, hub.createdAt);
                    case UPDATED_AT -> new OrderSpecifier<>(direction, hub.updatedAt);
                    case ID -> new OrderSpecifier<>(direction, hub.id);
                };
            })
            .toArray(OrderSpecifier[]::new);
    }

    private BooleanExpression keywordContains(String keyword) {
        return StringUtils.hasText(keyword) ? hub.name.value.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression typeEquals(HubType type) {
        return type != null ? hub.type.eq(type) : null;
    }
}
