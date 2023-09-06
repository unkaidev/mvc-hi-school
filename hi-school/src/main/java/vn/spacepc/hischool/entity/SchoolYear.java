package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class SchoolYear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long schoolYearId;

  private String schoolYearName;
  @OneToMany(mappedBy = "schoolYear")
  private List<Semester> semesters;



  public SchoolYear() {
  }

  public SchoolYear(Long schoolYearId, String schoolYearName, List<Semester> semesters) {
    this.schoolYearId = schoolYearId;
    this.schoolYearName = schoolYearName;
    this.semesters = semesters;
  }

  public Long getSchoolYearId() {
    return schoolYearId;
  }

  public void setSchoolYearId(Long schoolYearId) {
    this.schoolYearId = schoolYearId;
  }

  public String getSchoolYearName() {
    return schoolYearName;
  }

  public void setSchoolYearName(String schoolYearName) {
    this.schoolYearName = schoolYearName;
  }

  public List<Semester> getSemesters() {
    return semesters;
  }

  public void setSemesters(List<Semester> semesters) {
    this.semesters = semesters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchoolYear that = (SchoolYear) o;
    return Objects.equals(schoolYearId, that.schoolYearId) && Objects.equals(
        schoolYearName, that.schoolYearName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schoolYearId, schoolYearName);
  }
}

