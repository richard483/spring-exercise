package com.example.userCRUD.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
  private String name;
  private String email;
  private String password;
  private String address;
  private String role;
}
