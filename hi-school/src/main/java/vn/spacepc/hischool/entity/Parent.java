package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parents")
public class Parent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long parentId;

  private String phoneNumber;
  private String email;
  private String fullName;

  @OneToMany(mappedBy = "parent", cascade = {
      CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.EAGER)
  private List<Student> students = new ArrayList<>();


  public Parent() {
  }

  public Parent(Long parentId, String fullName, String phoneNumber, String email) {
    this.fullName = fullName;
    this.parentId = parentId;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public Parent(Long parentId, String phoneNumber, String email, String fullName,
      List<Student> students) {
    this.parentId = parentId;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.fullName = fullName;
    this.students = students;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

}

