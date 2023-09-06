package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Teacher;

@Service
public class TeacherServiceImpl implements TeacherService {

  private TeacherRepository teacherRepository;

  @Autowired

  public TeacherServiceImpl(TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }


  @Override
  public List<Teacher> getAllTeachers() {
    return teacherRepository.findAll();
  }

  @Override
  public Teacher getTeacherById(long id) {
    return teacherRepository.getById(id);
  }

  @Override
  @Transactional
  public Teacher addTeacher(Teacher teacher) {
    return teacherRepository.save(teacher);
  }

  @Override
  @Transactional
  public Teacher updateTeacher(Teacher teacher) {
    return teacherRepository.saveAndFlush(teacher);
  }

  @Override
  @Transactional
  public void deleteTeacherById(long id) {
    teacherRepository.deleteById(id);
  }

  @Override
  public Teacher findByEmail(Email email) {
    return teacherRepository.findByEmail(email);
  }

}
