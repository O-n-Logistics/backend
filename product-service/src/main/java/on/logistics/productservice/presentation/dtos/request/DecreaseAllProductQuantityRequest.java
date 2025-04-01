package on.logistics.productservice.presentation.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

public record DecreaseAllProductQuantityRequest(
    @NotEmpty List<DecreaseProductQuantityRequest> products
) {

    public record DecreaseProductQuantityRequest(
        @NotBlank UUID productId,
        @Min(value = 1, message = "재고 수량 감소는 한 개 미만일 수 없습니다.") Long decreaseQuantity
    ) {

    }

}
