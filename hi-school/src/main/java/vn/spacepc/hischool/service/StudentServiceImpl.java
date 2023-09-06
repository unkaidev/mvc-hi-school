package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;
  private EntityManager entityManager;

  @Autowired
  public StudentServiceImpl(StudentRepository studentRepository, EntityManager entityManager) {
    this.studentRepository = studentRepository;
    this.entityManager = entityManager;
  }

  @Override
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @Override
  public Student getStudentById(long id) {
    return studentRepository.findById(id).orElse(null);
  }

  @Override
  public List<Student> getStudentsByClassId(long classId) {
    String jpql = "SELECT s FROM Student s JOIN s.schoolClasses sc WHERE sc.classId = :classId";
    List<Student> students = entityManager.createQuery(jpql, Student.class)
        .setParameter("classId", classId)
        .getResultList();
    return students;
  }


  @Override
  @Transactional
  public Student addStudent(Student student) {
    return studentRepository.save(student);
  }

  @Override
  @Transactional
  public Student updateStudent(Student student) {
    return studentRepository.saveAndFlush(student);
  }

  @Override
  @Transactional
  public void deleteStudentById(long id) {
    studentRepository.deleteById(id);
  }

  @Override
  public Student findByEmail(Email email) {
    return studentRepository.findByEmail(email);
  }

}
