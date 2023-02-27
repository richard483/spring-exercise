package com.example.userCRUD.configurations;

import com.example.userCRUD.data.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LifeCycleConfiguration {

  @Bean
  Connection connection(){
    log.info("Connection bean made!");
    return  new Connection();
  }
}
