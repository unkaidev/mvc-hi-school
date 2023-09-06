package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Objects;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "school_class")
public class SchoolClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long classId;

  private String className;
  @ManyToOne
  @JoinColumn(name = "school_year_id")
  private SchoolYear schoolYear;

  // Khóa ngoại
  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToMany
      (
          fetch = FetchType.LAZY,
          cascade = {
              CascadeType.PERSIST,
              CascadeType.DETACH,
              CascadeType.REFRESH,
              CascadeType.MERGE})
  @JoinTable(
      name = "class_student",
      joinColumns = @JoinColumn(name = "class_id"),
      inverseJoinColumns = @JoinColumn(name = "student_id")
  )
  private Collection<Student> students;

  public SchoolClass() {
  }

  public SchoolClass(Long classId, String className, SchoolYear schoolYear, Teacher teacher) {
    this.classId = classId;
    this.className = className;
    this.schoolYear = schoolYear;
    this.teacher = teacher;
  }

  public SchoolClass(Long classId, String className, SchoolYear schoolYear, Teacher teacher,
      Collection<Student> students) {
    this.classId = classId;
    this.className = className;
    this.schoolYear = schoolYear;
    this.teacher = teacher;
    this.students = students;
  }

  public Long getClassId() {
    return classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public SchoolYear getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(SchoolYear schoolYear) {
    this.schoolYear = schoolYear;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Collection<Student> getStudents() {
    return students;
  }

  public void setStudents(Collection<Student> students) {
    this.students = students;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchoolClass that = (SchoolClass) o;
    return Objects.equals(classId, that.classId) && Objects.equals(className,
        that.className) && Objects.equals(schoolYear, that.schoolYear)
        && Objects.equals(teacher, that.teacher) && Objects.equals(students,
        that.students);
  }

  @Override
  public int hashCode() {
    return Objects.hash(classId, className, schoolYear, teacher, students);
  }
}
