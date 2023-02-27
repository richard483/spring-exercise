package com.example.userCRUD.configurations;

import com.example.userCRUD.data.Bar;
import com.example.userCRUD.data.Foo;
import com.example.userCRUD.data.FooBar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfiguration {

  @Bean
  public Foo foo(){
    return new Foo();
  }

  @Bean
  public Bar bar(){
    return new Bar();
  }

  @Bean
  public FooBar fooBar(Foo foo, Bar bar){
    return new FooBar(foo, bar);
  }
}
