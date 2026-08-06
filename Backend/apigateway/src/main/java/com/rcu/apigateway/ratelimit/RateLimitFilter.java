package com.rcu.apigateway.ratelimit;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {
    private final RedisRateLimiter rateLimiter;

    public RateLimitFilter(RedisRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientId = resolveClientId(request);
        String apiGroup = resolveApiGroup(request.getRequestURI());
        String bucketKey = "rl:" + clientId + ":" + apiGroup;

        if (rateLimiter.isAllowed(bucketKey)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\":\"rate_limit_exceeded\",\"message\":\"Too many requests, please slow down.\"}"
            );
        }
    }

    private String resolveClientId(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String resolveApiGroup(String uri) {
        if (uri.startsWith("/api/rcu")) return "rcu";
        if (uri.startsWith("/api/loans")) return "loans";
        if (uri.startsWith("/api/documents")) return "documents";
        if (uri.startsWith("/api/auth") || uri.startsWith("/api/users")) return "auth";
        return "other";
    }

}
