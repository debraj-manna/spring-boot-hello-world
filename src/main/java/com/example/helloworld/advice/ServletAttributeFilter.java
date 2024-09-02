package com.example.helloworld.advice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class ServletAttributeFilter extends OncePerRequestFilter  {
  static final String ATTRIBUTE = "customAttribute";
  @Override
  protected void doFilterInternal(
      @NotNull final HttpServletRequest request,
      @NotNull final HttpServletResponse response,
      @NotNull final FilterChain filterChain)
      throws ServletException, IOException {
    if (!request.isAsyncStarted()) {
      try {
        filterChain.doFilter(request, response);
      } finally {
        // Value of the attribute is used in HttpMetricCollectionFilter which has the
        // HIGHEST_PRECEDENCE and gets executed at the very last in post filter chain. So the
        // context gets cleared and is not accessible in HttpMetricCollectionFilter
        request.setAttribute(ATTRIBUTE, "Tuk");
      }
    }
  }
}
