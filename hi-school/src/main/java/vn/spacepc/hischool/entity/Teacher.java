package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teacherId;
  private String fullName;
  private String phoneNumber;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "email")
  private Email email;
  @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
  private Account account;

  public Teacher() {
  }

  public Teacher(Long teacherId, String fullName, String phoneNumber, Email email,
      Account account) {
    this.teacherId = teacherId;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.account = account;
  }

  public Long getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

}
