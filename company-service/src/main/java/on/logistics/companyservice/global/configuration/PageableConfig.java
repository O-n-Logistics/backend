package on.logistics.companyservice.global.configuration;

import java.util.List;
import lombok.RequiredArgsConstructor;
import on.logistics.companyservice.global.resolver.PageableVerificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class PageableConfig implements WebMvcConfigurer {

    private final PageableVerificationArgumentResolver pageableVerificationArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableVerificationArgumentResolver);
    }
}