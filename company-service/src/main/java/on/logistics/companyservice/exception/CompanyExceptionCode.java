package on.logistics.companyservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import on.logistics.companyservice.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyExceptionCode implements ExceptionCode {
    COMPANY_NAME_IS_NULL(HttpStatus.BAD_REQUEST, "업체 이름은 필수 입력 값입니다"),
    COMPANY_NAME_MAX_LENGTH(HttpStatus.BAD_REQUEST, "업체의 최대 이름은 100자를 초과할 수 없습니다."),
    COMPANY_ADDRESS_IS_NULL(HttpStatus.BAD_REQUEST, "업체 주소는 필수 입력 값입니다."),
    COMPANY_USER_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 등록된 업체가 있는 유저 아이디입니다."),
    COMPANY_IS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 업체입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}