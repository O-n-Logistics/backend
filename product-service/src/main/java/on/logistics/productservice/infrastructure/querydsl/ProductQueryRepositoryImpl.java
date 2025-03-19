package on.logistics.productservice.infrastructure.querydsl;

import static on.logistics.productservice.domain.QProduct.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import on.logistics.productservice.application.dto.SearchProductRequestDto;
import on.logistics.productservice.domain.Product;
import on.logistics.productservice.global.enums.PageSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProduct(SearchProductRequestDto requestDto) {
        BooleanBuilder builder = getSearchProductQuery(requestDto);
        List<Product> productList = searchProductList(builder, requestDto.pageable());
        Long total = totalCount(builder);
        return new PageImpl<>(productList, requestDto.pageable(), total);
    }

    private List<Product> searchProductList(BooleanBuilder builder, Pageable pageable) {
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable);
        return queryFactory
            .selectFrom(product)
            .where(builder)
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanBuilder getSearchProductQuery(SearchProductRequestDto cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (cond.name() != null) {
            builder.and(product.name.value.contains(cond.name()));
        }
        return builder;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        Sort sort = pageable.getSort();

        sort.forEach(order -> {
            String sortBy = order.getProperty();
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            switch (PageSortBy.valueOf(sortBy.toUpperCase())) {
                case CREATED_AT ->
                    orderSpecifiers.add(new OrderSpecifier<>(direction, product.createdAt));
                case UPDATED_AT ->
                    orderSpecifiers.add(new OrderSpecifier<>(direction, product.updatedAt));
                case ID -> orderSpecifiers.add(new OrderSpecifier<>(direction, product.id));
            }
        });

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private Long totalCount(BooleanBuilder builder) {
        return queryFactory
            .select(product.count())
            .from(product)
            .where(builder)
            .fetchOne();
    }
}
