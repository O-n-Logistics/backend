package on.logistics.productservice.application.dto;

import org.springframework.data.domain.Pageable;

public record SearchProductRequestDto(String name, Pageable pageable) {

    public static SearchProductRequestDto from(String productName, Pageable pageable) {
        return new SearchProductRequestDto(productName, pageable);
    }
}
