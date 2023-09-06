package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Student;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {

  private SchoolClassRepository schoolClassRepository;
  private EntityManager entityManager;

  @Autowired

  public SchoolClassServiceImpl(SchoolClassRepository schoolClassRepository,
      EntityManager entityManager) {
    this.schoolClassRepository = schoolClassRepository;
    this.entityManager = entityManager;
  }


  @Override
  public List<SchoolClass> getAllSchoolClasss() {
    return schoolClassRepository.findAll();
  }

  @Override
  public SchoolClass getSchoolClassById(long id) {
    return schoolClassRepository.getById(id);
  }

  @Override
  public List<SchoolClass> getSchoolClassByTeacherId(long teacherId) {
    String jpql = "SELECT s FROM SchoolClass s JOIN s.teacher t WHERE t.teacherId = :teacherId";
    List<SchoolClass> schoolClasses = entityManager.createQuery(jpql, SchoolClass.class)
        .setParameter("teacherId", teacherId)
        .getResultList();
    return schoolClasses;
  }


  @Override
  public List<SchoolClass> getSchoolClassBySchoolYearId(long schoolYearId) {
    String jpql = "SELECT s FROM SchoolClass s JOIN s.schoolYear sy WHERE sy.schoolYearId = :schoolYearId";
    List<SchoolClass> schoolClasses = entityManager.createQuery(jpql, SchoolClass.class)
        .setParameter("schoolYearId", schoolYearId)
        .getResultList();
    return schoolClasses;
  }


  @Override
  @Transactional
  public SchoolClass addSchoolClass(SchoolClass schoolClass) {
    return schoolClassRepository.save(schoolClass);
  }

  @Override
  @Transactional
  public SchoolClass updateSchoolClass(SchoolClass schoolClass) {
    return schoolClassRepository.saveAndFlush(schoolClass);
  }

  @Override
  @Transactional
  public void deleteSchoolClassById(long id) {
    schoolClassRepository.deleteById(id);
  }
}
