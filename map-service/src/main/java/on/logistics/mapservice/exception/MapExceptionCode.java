package on.logistics.mapservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import on.logistics.mapservice.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MapExceptionCode implements ExceptionCode {

    INVALID_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "API 응답이 올바르지 않습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 호출 중 문제가 발생하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
