package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long attendanceId;

  private Date attendanceDate;

  @ManyToOne
  private Teacher teacher;
  @ManyToOne
  private Student student;
  @ManyToOne
  private Schedule schedule;

  private boolean isPresent;


  public Attendance() {
  }

  public Attendance(Long attendanceId, Date attendanceDate, Teacher teacher, Student student,
      Schedule schedule, boolean isPresent) {
    this.attendanceId = attendanceId;
    this.attendanceDate = attendanceDate;
    this.teacher = teacher;
    this.student = student;
    this.schedule = schedule;
    this.isPresent = isPresent;
  }

  public Long getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(Long attendanceId) {
    this.attendanceId = attendanceId;
  }

  public Date getAttendanceDate() {
    return attendanceDate;
  }

  public void setAttendanceDate(Date attendanceDate) {
    this.attendanceDate = attendanceDate;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public boolean isPresent() {
    return isPresent;
  }

  public void setPresent(boolean present) {
    isPresent = present;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
}
