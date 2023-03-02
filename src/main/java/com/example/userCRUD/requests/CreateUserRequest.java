package com.example.userCRUD.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String name;
  private String email;
  private String password;
  private String address;
  private String role;
}
