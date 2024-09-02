package com.example.helloworld.advice;

import brave.propagation.CurrentTraceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
// has to run before org.springframework.web.filter.ServerHttpObservationFilter
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

  private static final String ATTRIBUTE = "customAttribute";

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    if (!request.isAsyncStarted()) {
      try {
        log("Before", request, response);
        filterChain.doFilter(request, response);
        log("After", request, response);
      } catch (Exception e) {
        log("Error", request, response);
        throw e;
      }
    }
  }

  private void log(final String msg, final HttpServletRequest request, final HttpServletResponse response) {
    val attr = request.getAttribute(ATTRIBUTE);
    log.info("{} URI: {}, Attribute Value: {}, Committed: {}", msg, request.getRequestURI(), Objects.requireNonNullElse(attr, ""), response.isCommitted());
  }
}
