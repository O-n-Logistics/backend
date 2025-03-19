package on.logistics.productservice.presentation.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import on.logistics.productservice.application.dto.CreateProductRequestDto;

public record CreateProductRequest(@NotBlank(message = "상품 이름은 필수 입력 값입니다.") String productName,
                                   @NotNull(message = "업체 ID는 필수로 입력되어야 합니다.") UUID companyId,
                                   @NotNull(message = "허브 ID는 필수로 입력되어야 합니다.") UUID managedHubId,
                                   @Min(value = -1, message = "상품 수량은 음수로 지정될 수 없습니다.") Long productQuantity,
                                   @Min(value = 1, message = "상품 가격은 0원 이하일 수 없습니다.") Long productPrice,
                                   @Min(value = 0, message = "번들 사이즈는 0원 이하일 수 없습니다.") Long bundleSize) {

    public static CreateProductRequestDto from(CreateProductRequest createProductRequest) {
        return new CreateProductRequestDto(createProductRequest.productName(),
            createProductRequest.companyId(), createProductRequest.managedHubId(),
            createProductRequest.productQuantity(), createProductRequest.productPrice(),
            createProductRequest.bundleSize());
    }
}