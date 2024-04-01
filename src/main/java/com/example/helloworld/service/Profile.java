package com.example.helloworld.service;

import com.example.helloworld.config.GrpcClientRetryConfig;
import lombok.val;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Import({GrpcClientRetryConfig.class})
@Retryable(interceptor = "grpcClientRetryInterceptor")
public class Profile {
  public String method1() {
    return method2();
  }

  public String method2() {
    val msg = "Hello World";
    System.out.println(msg);
    throw new RuntimeException("Testing");
  }
}
