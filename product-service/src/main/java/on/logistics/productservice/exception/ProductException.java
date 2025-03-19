package on.logistics.productservice.exception;

import on.logistics.productservice.global.exception.CustomException;
import on.logistics.productservice.global.exception.ExceptionCode;

public class ProductException extends CustomException {

    public ProductException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
