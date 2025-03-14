package on.logistics.orderservice.global.exception;

import lombok.extern.slf4j.Slf4j;
import on.logistics.orderservice.global.exception.pageable.PageableException;
import on.logistics.orderservice.global.presentation.dtos.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(PageableException.class)
  public ResponseEntity<?> handlePageableException(PageableException e) {
    log.warn("handlePageableException", e);
    ExceptionCode exceptionCode = e.getException();
    return handleExceptionInternal(exceptionCode);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException e) {
    log.warn("handleCustomException", e);
    ExceptionCode exceptionCode = e.getException();
    return handleExceptionInternal(exceptionCode);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
    log.warn("handleIllegalArgument", e);
    ExceptionCode exceptionCode = CommonExceptionCode.ILLEGAL_ARGUMENT;
    return handleExceptionInternal(e.getMessage(), exceptionCode);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAllException(Exception ex) {
    log.warn("handleAllException", ex);
    ExceptionCode exceptionCode = CommonExceptionCode.INTERNAL_SERVER_ERROR;
    return handleExceptionInternal(ex.getMessage(), exceptionCode);
  }


  private ResponseEntity<?> handleExceptionInternal(ExceptionCode exceptionCode) {
    return ResponseEntity.status(exceptionCode.getHttpStatus())
        .body(makeErrorResponse(exceptionCode));
  }

  private CommonResponse<?> makeErrorResponse(ExceptionCode exceptionCode) {
    return CommonResponse.exception(exceptionCode.getMessage());
  }

  private ResponseEntity<?> handleExceptionInternal(String message, ExceptionCode exceptionCode) {
    return ResponseEntity.status(exceptionCode.getHttpStatus())
        .body(makeErrorResponse(message));
  }

  private CommonResponse<?> makeErrorResponse(String message) {
    return CommonResponse.exception(message);
  }
}
