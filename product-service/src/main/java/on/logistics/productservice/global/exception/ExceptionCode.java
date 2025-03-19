package on.logistics.productservice.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    String name();

    HttpStatus getHttpStatus();

    String getMessage();
}