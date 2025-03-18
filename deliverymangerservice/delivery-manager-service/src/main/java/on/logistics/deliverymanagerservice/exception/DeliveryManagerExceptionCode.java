package on.logistics.deliverymanagerservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import on.logistics.deliverymanagerservice.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerExceptionCode implements ExceptionCode {

    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송 담당자를 찾을 수 없습니다"),
    SAME_DELIVERY_TYPE(HttpStatus.BAD_REQUEST, "이미 설정된 배송 타입입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
