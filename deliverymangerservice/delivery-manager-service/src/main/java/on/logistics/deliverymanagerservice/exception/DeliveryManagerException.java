package on.logistics.deliverymanagerservice.exception;

import on.logistics.deliverymanagerservice.global.exception.CustomException;
import on.logistics.deliverymanagerservice.global.exception.ExceptionCode;

public class DeliveryManagerException extends CustomException {

    public DeliveryManagerException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
