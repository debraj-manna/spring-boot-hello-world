package com.example.helloworld.aspects;

import com.example.helloworld.utils.JooqUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class DaoExceptionHandler {
  @AfterThrowing(
      pointcut =
          "execution(* *(..)) &&"
              + " @annotation(com.example.helloworld.utils.HandleDaoException)",
      throwing = "ex")
  public void handle(RuntimeException ex) {
    log.error("Exception received on dao", ex);
    throw JooqUtil.mapException(ex);
  }
}
