package on.logistics.orderservice.global.exception.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import on.logistics.orderservice.infrastructure.clients.exception.ExternalApiException.ExternalApiBadRequestException;
import on.logistics.orderservice.infrastructure.clients.exception.ExternalApiException.ExternalApiClientException;
import on.logistics.orderservice.infrastructure.clients.exception.ExternalApiException.ExternalApiDefaultException;
import on.logistics.orderservice.infrastructure.clients.exception.ExternalApiException.ExternalApiNotFoundException;
import on.logistics.orderservice.infrastructure.clients.exception.ExternalApiException.ExternalApiServerException;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int statusCode = response.status();
        return switch (statusCode / 100) {
            case 4 -> getClientException(statusCode);
            case 5 -> getServerException(statusCode);
            default -> new ExternalApiDefaultException();
        };
    }

    private static Exception getClientException(int statusCode) {
        return switch (statusCode) {
            case 400 -> new ExternalApiBadRequestException();
            case 404 -> new ExternalApiNotFoundException();
            default -> new ExternalApiClientException();
        };
    }

    private static ExternalApiServerException getServerException(int statusCode) {
        return switch (statusCode) {
            default -> new ExternalApiServerException();
        };
    }
}