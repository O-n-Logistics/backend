package on.logistics.companyservice.infrastructure.querydsl;

import static on.logistics.companyservice.domain.entity.QCompany.company;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.companyservice.application.dtos.request.SearchCompanyRequestDto;
import on.logistics.companyservice.domain.entity.Company;
import on.logistics.companyservice.global.enums.PageSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "CompanyQueryRepositoryImpl")
@Repository
@RequiredArgsConstructor
public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> searchCompany(SearchCompanyRequestDto requestDto) {
        BooleanBuilder builder = getSearchCompanyQuery(requestDto);
        List<Company> companyList = searchCompanyList(builder, requestDto.pageable());
        Long total = totalCount(builder);
        return new PageImpl<>(companyList, requestDto.pageable(), total);
    }

    private List<Company> searchCompanyList(BooleanBuilder builder, Pageable pageable) {
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable);
        return queryFactory.selectFrom(company).where(builder)
            .orderBy(orderSpecifiers).offset(pageable.getOffset()).limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanBuilder getSearchCompanyQuery(SearchCompanyRequestDto cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (cond.name() != null) {
            builder.and(company.name.value.contains(cond.name()));
        }

        if (cond.type() != null) {
            builder.and(company.type.eq(cond.type()));
        }
        return builder;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        Sort sort = pageable.getSort();

        sort.forEach(order -> {
            String sortBy = order.getProperty();
            Order direction = order.getDirection() == Sort.Direction.ASC ? Order.ASC : Order.DESC;

            switch (PageSortBy.valueOf(sortBy.toUpperCase())) {
                case CREATED_AT ->
                    orderSpecifiers.add(new OrderSpecifier<>(direction, company.createdAt));
                case UPDATED_AT ->
                    orderSpecifiers.add(new OrderSpecifier<>(direction, company.updatedAt));
                case ID -> orderSpecifiers.add(new OrderSpecifier<>(direction, company.id));
            }
        });

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private Long totalCount(BooleanBuilder builder) {
        return queryFactory.select(company.count()).from(company).where(builder).fetchOne();
    }
}
