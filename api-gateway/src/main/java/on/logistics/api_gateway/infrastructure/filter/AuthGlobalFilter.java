package on.logistics.api_gateway.infrastructure.filter;

import lombok.extern.slf4j.Slf4j;
import on.logistics.api_gateway.exception.ApiGatewayException;
import on.logistics.api_gateway.exception.ApiGatewayExceptionCode;
import on.logistics.api_gateway.global.presentation.dtos.CommonResponse;
import on.logistics.api_gateway.presentation.dtos.AuthValidateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j(topic = "AuthGlobalFilter")
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Value("${spring.cloud.gateway.auth_global_filter.login_url}")
    private String loginUrl;
    @Value("${spring.cloud.gateway.auth_global_filter.signup_url}")
    private String signupUrl;
    @Value("${spring.cloud.gateway.auth_global_filter.validate_endpoint}")
    private String validateEndpoint;

    private final WebClient webClient;

    public AuthGlobalFilter(
        WebClient.Builder webClientBuilder,
        @Value("${spring.cloud.gateway.auth_global_filter.base_url}") String baseUrl
    ) {
        this.webClient = webClientBuilder
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public Mono<Void> filter(
        ServerWebExchange exchange,
        GatewayFilterChain chain
    ) {
        log.info("Auth Global Filter");
        String path = exchange.getRequest().getURI().getPath();

        if (path.equals(loginUrl) || path.equals(signupUrl)) {
            return chain.filter(exchange);
        }

        log.info("Auth Global Filter Accept");
        String accessToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (accessToken == null || accessToken.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        var refreshCookie = exchange.getRequest().getCookies().getFirst("refreshToken");
        if (refreshCookie == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String refreshToken = refreshCookie.getValue();

        log.info("Token Extracted");
        return webClient.get()
            .uri(validateEndpoint)
            .header("Authorization", accessToken)
            .cookie("refreshToken", refreshToken)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CommonResponse<AuthValidateResponse>>() {})
            .flatMap(commonResponse -> {
                if (commonResponse.data() == null) {
                    log.error("Common Response Data Is Null");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                String passportId = commonResponse.data().passportId();
                log.info("Passport Id Extracted");
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .headers(httpHeaders -> {
                        httpHeaders.remove("Authorization");
                        httpHeaders.add("X-Passport-Id", passportId);
                    })
                    .build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            })
            .onErrorResume(ex -> {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            });
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
