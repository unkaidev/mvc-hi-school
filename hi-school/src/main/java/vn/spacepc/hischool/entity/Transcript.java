package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transcript {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transcriptId;

  private String semesterEvaluation;

  @ManyToOne
      (fetch = FetchType.EAGER,
          cascade = {
              CascadeType.PERSIST,
              CascadeType.DETACH,
              CascadeType.REFRESH,
              CascadeType.MERGE})
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "school_year_id")
  private SchoolYear schoolYear;

  public Transcript() {
  }

  public Transcript(Long transcriptId, String semesterEvaluation, Student student) {
    this.transcriptId = transcriptId;
    this.semesterEvaluation = semesterEvaluation;
    this.student = student;
  }

  public Transcript(Long transcriptId, String semesterEvaluation, Student student,
      SchoolYear schoolYear) {
    this.transcriptId = transcriptId;
    this.semesterEvaluation = semesterEvaluation;
    this.student = student;
    this.schoolYear = schoolYear;
  }

  public Long getTranscriptId() {
    return transcriptId;
  }

  public void setTranscriptId(Long transcriptId) {
    this.transcriptId = transcriptId;
  }

  public String getSemesterEvaluation() {
    return semesterEvaluation;
  }

  public void setSemesterEvaluation(String semesterEvaluation) {
    this.semesterEvaluation = semesterEvaluation;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public SchoolYear getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(SchoolYear schoolYear) {
    this.schoolYear = schoolYear;
  }

}


