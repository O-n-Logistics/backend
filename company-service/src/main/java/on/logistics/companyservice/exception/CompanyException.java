package on.logistics.companyservice.exception;

import on.logistics.companyservice.global.exception.CustomException;
import on.logistics.companyservice.global.exception.ExceptionCode;

public class CompanyException extends CustomException {

    public CompanyException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
