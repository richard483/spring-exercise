package com.example.userCRUD;

import com.example.userCRUD.configurations.LifeCycleConfiguration;
import com.example.userCRUD.data.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleTest {

  //  ApplicationContext applicationContext;
  ConfigurableApplicationContext applicationContext;

  @BeforeEach
  void setUp() {
    applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
    applicationContext.registerShutdownHook();
  }

  @AfterEach
  void tearDown() {
    //    applicationContext.close();
  }

  @Test
  void testConnection() {
    applicationContext.getBean(Connection.class);
    Assertions.assertNotNull(applicationContext);
  }
}
