package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long subjectId;

  private String subjectName;

  @ManyToOne
  @JoinColumn(name = "semester_id")
  private Semester semester;

  @OneToMany(mappedBy = "subject")
  private Set<Score> scores = new HashSet<>();

  public Subject() {
  }

  public Subject(Long subjectId, String subjectName, Semester semester, Set<Score> scores) {
    this.subjectId = subjectId;
    this.subjectName = subjectName;
    this.semester = semester;
    this.scores = scores;
  }

  public Long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Long subjectId) {
    this.subjectId = subjectId;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }


  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Set<Score> getScores() {
    return scores;
  }

  public void setScores(Set<Score> scores) {
    this.scores = scores;
  }
}
