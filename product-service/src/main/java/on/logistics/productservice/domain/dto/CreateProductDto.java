package on.logistics.productservice.domain.dto;

import java.util.UUID;
import on.logistics.productservice.application.dto.CreateProductRequestDto;

public record CreateProductDto(String productName, UUID companyId, UUID managedHubId,
                               Long productQuantity, Long productPrice, Long bundleSize) {

    public static CreateProductDto from(CreateProductRequestDto requestDto) {
        return new CreateProductDto(requestDto.productName(), requestDto.companyId(),
            requestDto.managedHubId(),
            requestDto.productQuantity(), requestDto.productPrice(), requestDto.bundleSize());
    }
}
