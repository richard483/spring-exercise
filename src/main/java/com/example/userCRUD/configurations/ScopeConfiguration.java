package com.example.userCRUD.configurations;

import com.example.userCRUD.data.Bar;
import com.example.userCRUD.data.Foo;
import com.example.userCRUD.scopes.DoubletoneScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
public class ScopeConfiguration {

  @Bean
  @Scope("prototype")
  public Foo foo(){
    log.info("Created new Foo!");
    return new Foo();
  }

  @Bean
  public CustomScopeConfigurer customScopeConfigurer(){
    CustomScopeConfigurer configurer = new CustomScopeConfigurer();
    configurer.addScope("doubleton", new DoubletoneScope());
    return configurer;
  }

  @Bean
  @Scope("doubleton")
  public Bar bar(){
    log.info("Created new Bar!");
    return new Bar();
  }
}


