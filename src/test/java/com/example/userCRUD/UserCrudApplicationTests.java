package com.example.userCRUD;

import com.example.userCRUD.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Sql(scripts = {"classpath:init.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureWebTestClient
class UserCrudApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void getAllUser_byDefaultPaging_success() {
    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/all").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
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
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['data'].['content'].length()")
        .isEqualTo(elements);
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
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no items for this page");
  }

  @Test
  void searchUser_withSearchString_success() throws Exception {
    String searchString = "ja";

    JSONObject response = null;

    response = new JSONObject(new String(webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/search").queryParam("name", searchString).build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['data'].['content']")
        .isNotEmpty()
        .returnResult()
        .getResponseBody()));

    // check if all response data has a substring of searched keyword
    for (int i = 0; i < response.getJSONObject("data").getJSONArray("content").length(); i++) {
      Assert.assertTrue(response.getJSONObject("data")
          .getJSONArray("content")
          .getJSONObject(1)
          .getString("name")
          .contains(searchString));
    }

  }

  @Test
  void searchUser_withNoItemsOnThePage_fail() {
    String searchString = "a";
    Integer elements = 8;
    Integer page = 10;

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/search")
            .queryParam("name", searchString)
            .queryParam("elements", elements)
            .queryParam("page", page)
            .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no items for this page");
  }

  @Test
  void searchUser_withNoItemsOnSearchResult_fail() {
    String searchString = "aasdasdasd";
    Integer elements = 8;
    Integer page = 1;

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/user/search")
            .queryParam("name", searchString)
            .queryParam("elements", elements)
            .queryParam("page", page)
            .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no results of your search");
  }

  @Test
  void createUser_withAllGivenValueOnBody_success() throws Exception {

    CreateUserRequest request = CreateUserRequest.builder()
        .name("Yae Sakura")
        .address("Hoyoverse")
        .email("yae@gmail.com")
        .role("MEMBER")
        .build();

    String requestJson = objectMapper.writeValueAsString(request);
    webTestClient.post()
        .uri("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(requestJson))
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("User " + request.getName() + " created!");
  }

  @Test
  void createUser_withNoGivenValueOnBody_fail() throws Exception {

    CreateUserRequest request = CreateUserRequest.builder().build();

    String requestJson = objectMapper.writeValueAsString(request);
    webTestClient.post()
        .uri("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(requestJson))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no given value of user!");
  }
}
