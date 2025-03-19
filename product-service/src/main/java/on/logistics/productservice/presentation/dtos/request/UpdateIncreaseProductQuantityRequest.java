package on.logistics.productservice.presentation.dtos.request;

import jakarta.validation.constraints.Min;
import java.util.UUID;
import on.logistics.productservice.application.dto.UpdateIncreaseProductQuantityRequestDto;

public record UpdateIncreaseProductQuantityRequest(
    @Min(value = 1, message = "재고 증가는 1개 미만일 수 없습니다.") Long productQuantity) {

    public static UpdateIncreaseProductQuantityRequestDto from(UUID productId,
        UpdateIncreaseProductQuantityRequest updateIncreaseProductQuantityRequest) {
        return new UpdateIncreaseProductQuantityRequestDto(productId,
            updateIncreaseProductQuantityRequest.productQuantity());
    }

}