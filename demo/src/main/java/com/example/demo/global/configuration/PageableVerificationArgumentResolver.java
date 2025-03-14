package com.example.demo.global.configuration;

import java.util.Arrays;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageableVerificationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private final SortArgumentResolver resolver = new SortHandlerMethodArgumentResolver();

    @Override
    public Pageable resolveArgument(
        MethodParameter methodParameter,
        @Nullable ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        @Nullable WebDataBinderFactory binderFactory
    ) {

        String pageText = webRequest.getParameter(
            getParameterNameToUse(getPageParameterName(), methodParameter));
        String sizeText = webRequest.getParameter(
            getParameterNameToUse(getSizeParameterName(), methodParameter));
        Sort sort = resolver.resolveArgument(methodParameter, mavContainer, webRequest,
            binderFactory);

        validatePage(pageText);
        validatePageSize(sizeText);
        validateSort(sort);

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private void validatePage(String pageText) {
        if (StringUtils.hasText(pageText)) {
            try {
                int page = Integer.parseInt(pageText);
                if (page < 0) {
                    throw new IllegalArgumentException("Page must be a non-negative integer");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Page must be an integer");
            }
        }
    }

    private void validatePageSize(String sizeText) {
        if (sizeText != null && !sizeText.isEmpty()) {
            try {
                int size = Integer.parseInt(sizeText);
                if (!PageSize.isValidSize(size)) {
                    throw new IllegalArgumentException(
                        "Invalid page size. Must be one of: " + Arrays.toString(PageSize.values()));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Page size must be an integer");
            }
        }
    }

    private void validateSort(Sort sort) {
        if (sort != null) {
            sort.forEach(order -> PageSortBy.valueOf(order.getProperty()));
        }
    }
}