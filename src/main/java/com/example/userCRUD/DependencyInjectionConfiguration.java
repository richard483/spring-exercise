package com.example.userCRUD;

import com.example.userCRUD.data.Bar;
import com.example.userCRUD.data.Foo;
import com.example.userCRUD.data.FooBar;
import org.springframework.context.annotation.Bean;

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
