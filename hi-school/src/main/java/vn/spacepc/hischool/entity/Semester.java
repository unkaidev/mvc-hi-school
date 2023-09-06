package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Semester {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long semesterId;

  private String semesterName;

  // Khóa ngoại
  @ManyToOne
  @JoinColumn(name = "school_year_id")
  private SchoolYear schoolYear;


  public Semester() {
  }

  public Semester(Long semesterId, String semesterName, SchoolYear schoolYear) {
    this.semesterId = semesterId;
    this.semesterName = semesterName;
    this.schoolYear = schoolYear;
  }

  public Long getSemesterId() {
    return semesterId;
  }

  public void setSemesterId(Long semesterId) {
    this.semesterId = semesterId;
  }

  public String getSemesterName() {
    return semesterName;
  }

  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  public SchoolYear getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(SchoolYear schoolYear) {
    this.schoolYear = schoolYear;
  }
}
