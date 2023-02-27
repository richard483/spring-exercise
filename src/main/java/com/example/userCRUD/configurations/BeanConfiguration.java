package com.example.userCRUD.configurations;

import com.example.userCRUD.data.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class BeanConfiguration {

  @Bean
  public Foo foo(){
    Foo foo = new Foo();
    log.info("Created new Foo");
    return  foo;
  }
}
