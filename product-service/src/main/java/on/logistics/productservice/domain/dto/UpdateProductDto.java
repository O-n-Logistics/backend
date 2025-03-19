package on.logistics.productservice.domain.dto;

import java.util.UUID;
import on.logistics.productservice.application.dto.UpdateProductRequestDto;

public record UpdateProductDto(UUID productId, String productName, Long productQuantity,
                               Long productPrice,
                               Long bundleSize) {

    public static UpdateProductDto from(UpdateProductRequestDto requestDto) {
        return new UpdateProductDto(requestDto.productId(), requestDto.productName(),
            requestDto.productQuantity(), requestDto.productPrice(), requestDto.bundleSize());
    }

}
