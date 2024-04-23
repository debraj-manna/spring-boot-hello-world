package com.example.helloworld.dao;

import com.example.helloworld.utils.HandleDaoException;
import org.springframework.stereotype.Component;

@Component
public class DaoImpl {
  @HandleDaoException
  public void getError() {
    throw new IllegalArgumentException("Test");
  }
}
