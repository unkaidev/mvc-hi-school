package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class TeacherAssignment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "teacher_assignment_class",
      joinColumns = @JoinColumn(name = "teacher_assignment_id"),
      inverseJoinColumns = @JoinColumn(name = "class_id")
  )
  private List<SchoolClass> classes;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @OneToMany(mappedBy = "teacherAssignment", cascade = CascadeType.ALL)
  private List<Score> scores;

  public TeacherAssignment() {
  }

  public TeacherAssignment(Long id, List<SchoolClass> classes, Teacher teacher, Subject subject,
      List<Score> scores) {
    this.id = id;
    this.classes = classes;
    this.teacher = teacher;
    this.subject = subject;
    this.scores = scores;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<SchoolClass> getClasses() {
    return classes;
  }

  public void setClasses(List<SchoolClass> classes) {
    this.classes = classes;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public List<Score> getScores() {
    return scores;
  }

  public void setScores(List<Score> scores) {
    this.scores = scores;
  }
}

