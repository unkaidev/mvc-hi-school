package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Semester;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;

@Service
public class SubjectServiceImpl {

  private SubjectRepository subjectRepository;
  private SemesterRepository semesterRepository;
  private TeacherRepository teacherRepository;

  @Autowired
  public SubjectServiceImpl(SubjectRepository subjectRepository,
      SemesterRepository semesterRepository,
      TeacherRepository teacherRepository) {
    this.subjectRepository = subjectRepository;
    this.semesterRepository = semesterRepository;
    this.teacherRepository = teacherRepository;
  }

  public List<Subject> getAllSubjects() {
    return subjectRepository.findAll();
  }

  public Subject getSubjectById(Long id) {
    return subjectRepository.findById(id).orElse(null);
  }

  public List<Semester> getAllSemesters() {
    return semesterRepository.findAll();
  }

  public List<Teacher> getAllTeachers() {
    return teacherRepository.findAll();
  }

  public void saveSubject(Subject subject) {
    subjectRepository.save(subject);
  }
}