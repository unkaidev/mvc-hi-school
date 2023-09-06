package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class School {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long schoolId;

  private String schoolName;


  public School() {
  }

  public School(Long schoolId, String schoolName) {
    this.schoolId = schoolId;
    this.schoolName = schoolName;
  }

  public Long getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Long schoolId) {
    this.schoolId = schoolId;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }
}

