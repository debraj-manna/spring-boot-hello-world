package com.example.helloworld.config;

import brave.Tracing;
import brave.Tracing.Builder;
import brave.TracingCustomizer;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.handler.SpanHandler;
import brave.propagation.B3Propagation;
import brave.propagation.CurrentTraceContext;
import brave.sampler.Sampler;
import com.example.helloworld.controller.HelloWorldController;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class ZipkinReporterConfig {
  private static final String SPANS_ENDPOINT = "/spans";

  static {
    // set this system property for the fields to be available in logger MDC context.
    System.setProperty(
        "management.tracing.baggage.correlation.fields",
        String.join(",", HelloWorldController.CTX_KEY));
  }

//  @Bean
//  @Primary
//  public ZipkinProperties zipkinProperties(final ResourceDirectory resourceDirectory) {
//    val zipkinConfig = new ZipkinProperties();
//    zipkinConfig.setEndpoint(
//        resourceDirectory.getNetwork(ApplicationType.ZIPKIN).getOrThrow().getIp() + SPANS_ENDPOINT);
//
//    return zipkinConfig;
//  }

  @Bean
  @Profile("!test")
  public Tracing braveTracing(
      Environment environment,
      List<SpanHandler> spanHandlers,
      List<TracingCustomizer> tracingCustomizers,
      CurrentTraceContext currentTraceContext) {

    // defining own Tracing bean instead of using the micrometer-tracing provided default bean for
    // two reasons:
    //  - set traceId to shorter non-128 bit format.
    //  - set B3 as header propagation format instead of W3C.
    //  - always sample (sampling probability = 1)
    //
    // This function is a modified version of
    // org.springframework.boot.actuate.autoconfigure.tracing.BraveAutoConfiguration.braveTracing()
    // with the above changes.

    val propagationFactory = BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY);
    propagationFactory.add(
        BaggagePropagationConfig.SingleBaggageField.remote(BaggageField.create(HelloWorldController.CTX_KEY)));

    String applicationName =
        environment.getProperty("spring.application.name", "DEFAULT_APPLICATION_NAME");
    Builder builder =
        Tracing.newBuilder()
            .currentTraceContext(currentTraceContext)
            .traceId128Bit(false)
            .supportsJoin(false)
            .propagationFactory(propagationFactory.build())
            .sampler(Sampler.ALWAYS_SAMPLE)
            .localServiceName(applicationName);
    spanHandlers.forEach(builder::addSpanHandler);
    for (TracingCustomizer tracingCustomizer : tracingCustomizers) {
      tracingCustomizer.customize(builder);
    }
    return builder.build();
  }
}

