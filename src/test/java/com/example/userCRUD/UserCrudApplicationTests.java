package com.example.userCRUD;

import com.example.userCRUD.constants.ERole;
import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:init.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserCrudApplicationTests {

  @Autowired UserRepository userRepository;

  @Test
  void userRepositoryNotNull() {
    Assertions.assertNotNull(userRepository.save(User.builder()
        .name("Richard")
        .email("ini email")
        .password("inipassword")
        .role(ERole.valueOf("MEMBER"))
        .build()));
  }

  @Test
  void createUserNotNull() {
    userRepository.save(User.builder()
        .name("Richard")
        .email("ini email")
        .password("inipassword")
        .role(ERole.valueOf("MEMBER"))
        .build());
    log.info(userRepository.findAll().toString());
    Assertions.assertNotNull(userRepository.findAll());
  }
}
