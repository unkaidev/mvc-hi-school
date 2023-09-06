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
import jakarta.persistence.OneToOne;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Timetable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long timetableId;

  private Date studyDate;

  @ManyToOne
  @JoinColumn(name = "semester_id")
  private Semester semester;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.DETACH,
      CascadeType.REFRESH,
      CascadeType.MERGE})
  @JoinTable(
      name = "timetable_schedule",
      joinColumns = @JoinColumn(name = "timetable_id"),
      inverseJoinColumns = @JoinColumn(name = "schedule_id")
  )
  private Collection<Schedule> schedules;
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.DETACH,
      CascadeType.REFRESH,
      CascadeType.MERGE})
  @JoinTable(
      name = "timetable_class",
      joinColumns = @JoinColumn(name = "timetable_id"),
      inverseJoinColumns = @JoinColumn(name = "class_id")
  )
  private Collection<SchoolClass> schoolClasses;
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.DETACH,
      CascadeType.REFRESH,
      CascadeType.MERGE})
  @JoinTable(
      name = "timetable_teacher",
      joinColumns = @JoinColumn(name = "timetable_id"),
      inverseJoinColumns = @JoinColumn(name = "teacher_id")
  )
  private Collection<Teacher> teachers;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.DETACH,
      CascadeType.REFRESH,
      CascadeType.MERGE})
  @JoinTable(
      name = "timetable_subject",
      joinColumns = @JoinColumn(name = "timetable_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id")
  )
  private Collection<Subject> subjects;
  public Timetable() {
  }

  public Timetable(Long timetableId, Date studyDate, Semester semester,
      Collection<Schedule> schedules, Collection<SchoolClass> schoolClasses,
      Collection<Teacher> teachers, Collection<Subject> subjects) {
    this.timetableId = timetableId;
    this.studyDate = studyDate;
    this.semester = semester;
    this.schedules = schedules;
    this.schoolClasses = schoolClasses;
    this.teachers = teachers;
    this.subjects = subjects;
  }

  public Long getTimetableId() {
    return timetableId;
  }

  public void setTimetableId(Long timetableId) {
    this.timetableId = timetableId;
  }

  public Date getStudyDate() {
    return studyDate;
  }

  public void setStudyDate(Date studyDate) {
    this.studyDate = studyDate;
  }


  public Semester getSemester() {
    return semester;
  }


  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Collection<Schedule> getSchedules() {
    return schedules;
  }

  public void setSchedules(Collection<Schedule> schedules) {
    this.schedules = schedules;
  }

  public Collection<SchoolClass> getSchoolClasses() {
    return schoolClasses;
  }

  public void setSchoolClasses(Collection<SchoolClass> schoolClasses) {
    this.schoolClasses = schoolClasses;
  }

  public Collection<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(Collection<Teacher> teachers) {
    this.teachers = teachers;
  }

  public Collection<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(Collection<Subject> subjects) {
    this.subjects = subjects;
  }

}
