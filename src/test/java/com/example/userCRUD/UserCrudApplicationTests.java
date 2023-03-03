package com.example.userCRUD;

import com.example.userCRUD.constants.ERole;
import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
import com.example.userCRUD.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Instant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
//@Sql(scripts = {"classpath:init.sql"},
//    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureWebTestClient
class UserCrudApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @Autowired private UserRepository userRepository;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void beforEach() {
    this.userRepository.save(User.builder()
        .id(1)
        .name("admin")
        .email("admin@localhost")
        .password("admin")
        .role(ERole.valueOf("ADMIN"))
        .address("Admin's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(2)
        .name("richard")
        .email("richard@localhost")
        .password("richard")
        .role(ERole.valueOf("MEMBER"))
        .address("Richard's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(3)
        .name("james")
        .email("james@localhost")
        .password("james")
        .role(ERole.valueOf("MEMBER"))
        .address("James's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(4)
        .name("jane")
        .email("jane@localhost")
        .password("jane")
        .role(ERole.valueOf("MEMBER"))
        .address("Jane's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(5)
        .name("john")
        .email("john@localhost")
        .password("john")
        .role(ERole.valueOf("MEMBER"))
        .address("John's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(6)
        .name("jim")
        .email("jim@localhost")
        .password("jim")
        .role(ERole.valueOf("MEMBER"))
        .address("Jim's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(7)
        .name("jenny")
        .email("jenny@localhost")
        .password("jenny")
        .role(ERole.valueOf("MEMBER"))
        .address("Jenny's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(8)
        .name("jessica")
        .email("jessica@localhost")
        .password("jessica")
        .role(ERole.valueOf("MEMBER"))
        .address("Jessica's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(9)
        .name("ewiwa")
        .email("ewiwa@localhost")
        .password("ewiwa")
        .role(ERole.valueOf("MEMBER"))
        .address("Ewiwa's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(10)
        .name("wosemi")
        .email("wosemi@localhost")
        .password("wosemi")
        .role(ERole.valueOf("MEMBER"))
        .address("Wosemi's house")
        .createdDate(Instant.now())
        .build());
    this.userRepository.save(User.builder()
        .id(11)
        .name("selen")
        .email("selen@localhost")
        .password("selen")
        .role(ERole.valueOf("MEMBER"))
        .address("Selen's house")
        .createdDate(Instant.now())
        .build());
  }

  @AfterEach
  void afterEach() {
    this.userRepository.deleteAll();
  }

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

  @Test
  void deleteUSer_withGivenId_success() {
    User user = userRepository.findUserByNameContains("john").get(0);
    Integer id = user.getId();
    String name = user.getName(); // based on init.sql

    webTestClient.delete()
        .uri(uriBuilder -> uriBuilder.path("/user").queryParam("id", id).build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("User: " + name + " deleted!");

    Assertions.assertNull(userRepository.findUserById(id));

  }

  @Test
  void deleteUser_withGivenWrongId_fail() {
    Integer id = 20;

    webTestClient.delete()
        .uri(uriBuilder -> uriBuilder.path("/user").queryParam("id", id).build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no user with such id");

  }

  @Test
  void updateUser_withGivenObjectValue_success() {
    User userTemp = userRepository.findUserByNameContains("john").get(0);
    Integer id = userTemp.getId();
    User user = User.builder()
        .name("Takina")
        .email("sakana@ricorico.co.jp")
        .password("iki password")
        .address("cafe")
        .role(ERole.valueOf("MEMBER"))
        .build();

    webTestClient.patch()
        .uri(uriBuilder -> uriBuilder.path("/user/" + id).build())
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(user))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("User: " + userTemp.getName() + " updated to " + user.getName() + " !");
  }

  @Test
  void updateUser_withNotAllGivenObjectValue_success() {
    User userTemp = userRepository.findUserByNameContains("john").get(0);
    Integer id = userTemp.getId();
    User user = User.builder()
        .name("Takina")
        .email("sakana@ricorico.co.jp")
        .password("iki password")
        .build();

    webTestClient.patch()
        .uri(uriBuilder -> uriBuilder.path("/user/" + id).build())
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(user))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("User: " + userTemp.getName() + " updated to " + user.getName() + " !");
  }

  @Test
  void updateUser_withNoGivenObjectValue_fail() {
    Integer id = 2;
    User user = User.builder().build();

    webTestClient.patch()
        .uri(uriBuilder -> uriBuilder.path("/user/" + id).build())
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(user))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no value of user attribute given");
  }

  @Test
  void updateUser_withFalseId_fail() {
    Integer id = 99999;
    User user = User.builder()
        .name("Takina")
        .email("sakana@ricorico.co.jp")
        .password("iki password")
        .build();

    webTestClient.patch()
        .uri(uriBuilder -> uriBuilder.path("/user/" + id).build())
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(user))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$['message']")
        .isEqualTo("There are no user with such id");
  }
}
