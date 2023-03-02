package com.example.userCRUD;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Sql(scripts = {"classpath:init.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureWebTestClient
class UserCrudApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @Test
  void getAllUser_byDefaultPaging_success() {
    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/all").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$['data'].['content'].length()")
        .isEqualTo(5); //paging = 5 by default
  }

  @Test
  void getAllUser_byPreDefinedPaging_success() {
    Integer elements = 8;

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/all").queryParam("elements", elements).build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$['data'].['content'].length()")
        .isEqualTo(8); //paging = 5 by default
  }

  @Test
  void getAllUser_withNoItemsOnThePage_fail() {
    Integer elements = 8;
    Integer page = 3;

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/all")
            .queryParam("elements", elements)
            .queryParam("page", page)
            .build())
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no items for this page");
  }
}
