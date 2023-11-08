package com.springboot.meetMyLecturer.config;


import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RequestConfig implements Filter {

    private final LoadingCache<String, Bucket> requestCountsPerIpAddress;

    public RequestConfig(){
        super();
        requestCountsPerIpAddress = Caffeine.newBuilder().
                expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Bucket>() {
                    public Bucket load(String key) {
                        return Bucket.builder()
                                .addLimit(Bandwidth.classic(15, Refill.intervally(1, Duration.ofMinutes(3))))
                                .build();
                    }
                });
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String clientIpAddress = getClientIP(httpServletRequest);

        String requestURI = httpServletRequest.getRequestURI();

        if (shouldApplyFilter(requestURI)) {
            if (!IsRequestValid(clientIpAddress)) {
                httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public String getClientIP(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (header == null){
            return request.getRemoteAddr();
        }
        return header.split(",")[0];
    }

    private boolean IsRequestValid(String ipAddress) {
        return requestCountsPerIpAddress.get(ipAddress).tryConsume(1);
    }

    /*private void insertIntoCache(String ipAddress) {
        requestCountsPerIpAddress.put(ipAddress,
                Bucket.builder()
                        .addLimit(Bandwidth.classic(2, Refill.intervally(1, Duration.ofMinutes(5)))) // set your request rate limit
                        .build());
    }*/

    private boolean shouldApplyFilter(String requestURI) {

        return requestURI.startsWith("/api/v1/user/profile/**");
    }
}
