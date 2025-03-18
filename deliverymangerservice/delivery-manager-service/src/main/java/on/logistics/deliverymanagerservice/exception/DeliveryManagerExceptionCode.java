package on.logistics.deliverymanagerservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import on.logistics.deliverymanagerservice.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerExceptionCode implements ExceptionCode {

    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 허브를 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
