package com.talan.academy.dto;

import java.util.List;

public class JwtReponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String pseudo;
  private String email;
  private List<String> roles;

  public JwtReponse(String accessToken, Long id, String email, String pseudo, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.email = email;
    this.pseudo = pseudo;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public List<String> getRoles() {
    return roles;
  }
}
