package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Teacher;

public interface TeacherService {
  public List<Teacher> getAllTeachers();
  public Teacher getTeacherById(long id);
  public Teacher addTeacher(Teacher teacher);
  public Teacher updateTeacher(Teacher teacher);
  public void deleteTeacherById(long id);

  Teacher findByEmail(Email email);

}
