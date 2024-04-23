package com.example.helloworld.utils;

import lombok.experimental.UtilityClass;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

@UtilityClass
public class JooqUtil {
  public static RuntimeException mapException(final RuntimeException e) {
    if (e instanceof DuplicateKeyException) {
      // Duplicate key exception
      return new IllegalArgumentException(e);
    }
    return e;
  }
}
