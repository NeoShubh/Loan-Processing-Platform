package com.example.loanapplication.rcu_service.external.FeinClient.interceptor;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)
                            RequestContextHolder.getRequestAttributes();

            if (attributes != null) {

                String authHeader =
                        attributes.getRequest()
                                .getHeader("Authorization");

                if (authHeader != null) {
                    requestTemplate.header(
                            "Authorization",
                            authHeader
                    );
                }
            }
        };
    }
}