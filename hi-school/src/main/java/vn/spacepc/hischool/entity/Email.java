package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Email {

  @Id
  private String email;

  public Email() {
  }

  public Email(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return email;
  }
}

