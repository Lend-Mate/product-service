package com.project.lendmate.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("[REQUEST] {} {}", request.getMethod(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);

        if (log.isDebugEnabled()) {
            log.debug("[RESPONSE] {} {} | status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
        }
    }
}
