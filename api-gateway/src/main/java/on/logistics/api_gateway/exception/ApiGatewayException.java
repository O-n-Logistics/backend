package on.logistics.api_gateway.exception;

import on.logistics.api_gateway.global.exception.CustomException;
import on.logistics.api_gateway.global.exception.ExceptionCode;

public class ApiGatewayException extends CustomException {
    public ApiGatewayException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
