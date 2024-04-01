package com.example.helloworld.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * A class which needs to access this retry config need to add the below annotations at the class
 * level
 *
 * <ul>
 *   <li>@EnableRetry
 *   <li>@Retryable(interceptor = "grpcClientRetryInterceptor", label = "ProfileGrpcClient")
 * </ul>
 *
 * <p>lable is mainly for logging
 *
 * <p>It will retry all public methods in the annotated class. If there is a chain of public methods
 * invocation then retry will be applicable on the top level public method. For example, let's say
 * in the below methods with maxAttempt set to 2 then invocation of method2() will be retried twice
 * if method1 is throwing an eligible retry exception even and no retry on method1()
 *
 * <p>public void method1() {} public void method2() {method1()}
 *
 * <p>Reference
 *
 * <ol>
 *   <li><a href="https://stackoverflow.com/a/57346976/785523">Link 1</a>
 *   <li><a href="https://stackoverflow.com/q/69421845/785523">Link 2</a>
 *   <li><a href="https://stackoverflow.com/a/68456808">Link 3</a>
 * </ol>
 */
@Configuration
@Slf4j
@EnableRetry
public class GrpcClientRetryConfig {

  @Bean
  public RetryOperationsInterceptor grpcClientRetryInterceptor() {

    val simpleRetry = new SimpleRetryPolicy();
    simpleRetry.setMaxAttempts(2);
    val template = new RetryTemplate();
    val interceptor = new RetryOperationsInterceptor();
    template.setRetryPolicy(simpleRetry);
    interceptor.setRetryOperations(template);
    return interceptor;
  }
}

