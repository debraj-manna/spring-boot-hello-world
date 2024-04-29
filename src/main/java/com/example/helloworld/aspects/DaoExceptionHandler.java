package com.example.helloworld.aspects;

import com.example.helloworld.utils.JooqUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Aspect
@Slf4j
@Configurable
public class DaoExceptionHandler {
  @Autowired
  private Map<Long, String> errorLogMap;

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
