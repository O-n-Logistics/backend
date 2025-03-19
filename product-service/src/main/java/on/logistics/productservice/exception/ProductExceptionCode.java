package on.logistics.productservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import on.logistics.productservice.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductExceptionCode implements ExceptionCode {
    PRODUCT_NAME_IS_NULL(HttpStatus.BAD_REQUEST, "상품 이름은 필수 입력 값입니다."),
    PRODUCT_NAME_MAX_LENGTH(HttpStatus.BAD_REQUEST, "상품 이름은 100자를 초과할 수 없습니다."),
    PRODUCT_PRICE_MIN(HttpStatus.BAD_REQUEST, "상품 가격은 0원 이하일 수 없습니다."),
    PRODUCT_QUANTITY_MIN(HttpStatus.BAD_REQUEST, "상품 재고는 음수일 수 없습니다."),
    PRODUCT_QUANTITY_LIMIT(HttpStatus.BAD_REQUEST, "상품 재고가 모두 소진되었습니다."),
    PRODUCT_BUNDLE_SIZE_MIN(HttpStatus.BAD_REQUEST, "번들 사이즈는 0개 이하일 수 없습니다."),
    PRODUCT_IS_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
