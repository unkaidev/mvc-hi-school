package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.TimetableRepository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Timetable;
@Service
public class TimetableServiceImpl implements TimetableService{


  private TimetableRepository timetableRepository;

  private EntityManager entityManager;
  @Autowired
  public TimetableServiceImpl(TimetableRepository timetableRepository, EntityManager entityManager) {
    this.timetableRepository = timetableRepository;
    this.entityManager = entityManager;
  }

  @Override
  public List<Timetable> getAllTimetables() {
    return timetableRepository.findAll();
  }

  @Override
  public Timetable getTimetableById(long id) {
    return timetableRepository.getById(id);
  }

  @Override
  public List<Timetable> getTimetablesBySemesterId(long semesterId) {
    String jpql = "SELECT r FROM Timetable r JOIN r.semester a WHERE a.id = :semesterId";
    List<Timetable> timetables = entityManager.createQuery(jpql, Timetable.class)
        .setParameter("semesterId", semesterId)
        .getResultList();
    return timetables;
  }

  @Override
  public List<Timetable> getTimetablesBySchoolClass(long schoolClassId) {
    String jpql = "SELECT DISTINCT t FROM Timetable t " +
        "JOIN t.timetableClasses tc " +
        "JOIN tc.schoolClass c " +
        "WHERE c.schoolClassId = :schoolClassId";

    List<Timetable> timetables = entityManager.createQuery(jpql, Timetable.class)
        .setParameter("schoolClassId", schoolClassId)
        .getResultList();
    return timetables;
  }

  @Override
  public List<SchoolClass> getSchoolClassesByTimetable(long timetableId) {
    String sql = "SELECT c.* FROM school_class c " +
        "JOIN timetable_class tc ON c.class_id = tc.class_id " +
        "WHERE tc.timetable_id = :timetableId";

    List<SchoolClass> schoolClasses = entityManager.createNativeQuery(sql, SchoolClass.class)
        .setParameter("timetableId", timetableId)
        .getResultList();
    return schoolClasses;
  }


  @Override
  @Transactional
  public Timetable addTimetable(Timetable timetable) {
    return timetableRepository.save(timetable);
  }

  @Override
  @Transactional
  public Timetable updateTimetable(Timetable timetable) {
    return timetableRepository.saveAndFlush(timetable);
  }

  @Override
  @Transactional
  public void deleteTimetableById(long id) {
    timetableRepository.deleteById(id);
  }
}
