package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Teaching {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teachingId;
  private Date teachingDate;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "class_id")
  private SchoolClass schoolClass;

  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  private String classComments;


  public Teaching() {
  }

  public Teaching(Long teachingId, Date teachingDate, Teacher teacher, SchoolClass schoolClass,
      Schedule schedule, Subject subject, String classComments) {
    this.teachingId = teachingId;
    this.teachingDate = teachingDate;
    this.teacher = teacher;
    this.schoolClass = schoolClass;
    this.schedule = schedule;
    this.subject = subject;
    this.classComments = classComments;
  }

  public Long getTeachingId() {
    return teachingId;
  }

  public void setTeachingId(Long teachingId) {
    this.teachingId = teachingId;
  }

  public Date getTeachingDate() {
    return teachingDate;
  }

  public void setTeachingDate(Date teachingDate) {
    this.teachingDate = teachingDate;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public SchoolClass getSchoolClass() {
    return schoolClass;
  }

  public void setSchoolClass(SchoolClass schoolClass) {
    this.schoolClass = schoolClass;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public String getClassComments() {
    return classComments;
  }

  public void setClassComments(String classComments) {
    this.classComments = classComments;
  }
}
