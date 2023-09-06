package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Student;

public interface StudentService {
  public List<Student> getAllStudents();
  public Student getStudentById(long id);

  List<Student> getStudentsByClassId(long schoolClassId);

  public Student addStudent(Student student);
  public Student updateStudent(Student student);
  public void deleteStudentById(long id);

  Student findByEmail(Email email);
}
