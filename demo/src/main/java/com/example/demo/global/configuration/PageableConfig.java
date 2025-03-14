package com.example.demo.global.configuration;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class PageableConfig implements WebMvcConfigurer {

    private final PageableVerificationArgumentResolver pageableVerificationArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableVerificationArgumentResolver);
        resolvers.add(fallbackPageableResolver());
    }

    public PageableHandlerMethodArgumentResolver fallbackPageableResolver() {
        final PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        final int DEFAULT_PAGE = 1;
        resolver.setFallbackPageable(PageRequest.of(
            DEFAULT_PAGE,
            PageSize.DEFAULT.getSize(),
            Direction.ASC,
            PageSortBy.CREATED_AT.getSortBy(), PageSortBy.UPDATED_AT.getSortBy()
        ));
        return resolver;
    }

}