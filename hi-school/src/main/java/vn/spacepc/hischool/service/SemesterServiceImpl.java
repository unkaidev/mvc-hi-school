package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.entity.Semester;

@Service
public class SemesterServiceImpl implements SemesterService {


  private SemesterRepository semesterRepository;

  private EntityManager entityManager;

  @Autowired
  public SemesterServiceImpl(SemesterRepository semesterRepository, EntityManager entityManager) {
    this.semesterRepository = semesterRepository;
    this.entityManager = entityManager;
  }

  @Override
  public List<Semester> getAllSemesters() {
    return semesterRepository.findAll();
  }

  @Override
  public List<Semester> getSemestersBySchoolYearId(long SchooolYearId) {
    String jpql = "SELECT r FROM Semester r JOIN r.schoolYear a WHERE a.id = :schooolYearId";
    List<Semester> semesters = entityManager.createQuery(jpql, Semester.class)
        .setParameter("schooolYearId", SchooolYearId)
        .getResultList();
    return semesters;
  }

  @Override
  public Semester getSemesterById(long id) {
    return semesterRepository.getById(id);
  }

  @Override
  @Transactional
  public Semester addSemester(Semester semester)   {
    return semesterRepository.save(semester);
  }

  @Override
  @Transactional
  public Semester updateSemester(Semester semester) {
    return semesterRepository.saveAndFlush(semester);
  }

  @Override
  @Transactional
  public void deleteSemesterById(long id) {
    semesterRepository.deleteById(id);
  }
}
