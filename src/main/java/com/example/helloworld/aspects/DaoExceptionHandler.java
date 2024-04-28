package com.example.helloworld.aspects;

import com.example.helloworld.utils.JooqUtil;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@AllArgsConstructor
@Component
public class DaoExceptionHandler {
  private final Map<Long, String> errorLogMap;

  @AfterThrowing(
      pointcut =
          "execution(* *(..)) &&"
              + " @annotation(com.example.helloworld.utils.HandleDaoException)",
      throwing = "ex")
  public void handle(RuntimeException ex) {
    errorLogMap.put(System.currentTimeMillis(), ex.getMessage());
    log.error("Exception received on dao", ex);
    log.error("Error Log Map Content: {}", errorLogMap);
    throw JooqUtil.mapException(ex);
  }
}
