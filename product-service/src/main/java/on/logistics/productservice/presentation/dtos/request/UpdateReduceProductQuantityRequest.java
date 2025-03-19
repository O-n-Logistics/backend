package on.logistics.productservice.presentation.dtos.request;

import jakarta.validation.constraints.Min;
import java.util.UUID;
import on.logistics.productservice.application.dto.UpdateReduceProductQuantityRequestDto;

public record UpdateReduceProductQuantityRequest(
    @Min(value = 1, message = "재고 수량 감소는 한 개 미만일 수 없습니다.") Long productQuantity) {

    public static UpdateReduceProductQuantityRequestDto from(UUID productId,
        UpdateReduceProductQuantityRequest updateReduceProductQuantityRequest) {
        return new UpdateReduceProductQuantityRequestDto(productId,
            updateReduceProductQuantityRequest.productQuantity());
    }

}