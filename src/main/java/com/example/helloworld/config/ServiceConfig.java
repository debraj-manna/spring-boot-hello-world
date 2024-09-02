package com.example.helloworld.config;

import brave.propagation.CurrentTraceContext;
import brave.propagation.StrictCurrentTraceContext;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextScheduledExecutorService;
import io.micrometer.context.ContextSnapshot;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServiceConfig {

  // Following async context propogation is taken from this doc:
  // https://github.com/micrometer-metrics/tracing/wiki/Spring-Cloud-Sleuth-3.1-Migration-Guide#async-instrumentation
  // Async Servlets setup
  @Configuration(proxyBeanMethods = false)
  @EnableAsync
  static class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
      return ContextExecutorService.wrap(
          Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("my-sad-thread-%d").build()), ContextSnapshot::captureAll);
    }
  }

  /**
   * NAME OF THE BEAN IS IMPORTANT!
   *
   * <p>We need to wrap this for @Async related things to propagate the context.
   *
   * @see EnableAsync
   */
  // [Observability] instrumenting executors
  @Bean(name = "taskExecutor", destroyMethod = "shutdown")
  ThreadPoolTaskScheduler threadPoolTaskScheduler() {
    final ThreadPoolTaskScheduler threadPoolTaskScheduler =
        new ThreadPoolTaskScheduler() {
          @Override
          protected ExecutorService initializeExecutor(
              final ThreadFactory threadFactory,
              final RejectedExecutionHandler rejectedExecutionHandler) {
            final ExecutorService executorService =
                super.initializeExecutor(threadFactory, rejectedExecutionHandler);
            return ContextExecutorService.wrap(executorService, ContextSnapshot::captureAll);
          }

          @Override
          public ScheduledExecutorService getScheduledExecutor() throws IllegalStateException {
            return ContextScheduledExecutorService.wrap(super.getScheduledExecutor());
          }
        };
    threadPoolTaskScheduler.setThreadNamePrefix("SpTaskExecutor1");
    threadPoolTaskScheduler.initialize();
    return threadPoolTaskScheduler;
  }

  // Global CORS policy.
  /** Configure async support for Spring MVC. */
  @Bean
  public WebMvcConfigurer webMvcConfigurerConfigurer(final AsyncTaskExecutor taskExecutor) {
    return new WebMvcConfigurer() {
      @Override
      public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(taskExecutor);
        // Added timeout of 60 seconds for async api due to downloads api having 100K rows.
        configurer.setDefaultTimeout(60 * 1000L /* 60 sec*/);
      }
    };
  }
}


