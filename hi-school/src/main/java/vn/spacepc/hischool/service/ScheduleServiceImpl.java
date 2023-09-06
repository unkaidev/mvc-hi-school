package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import vn.spacepc.hischool.dao.ScheduleRepository;
import vn.spacepc.hischool.entity.Schedule;
import vn.spacepc.hischool.entity.SchoolClass;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;
  private EntityManager entityManager;

  public ScheduleServiceImpl(ScheduleRepository scheduleRepository, EntityManager entityManager) {
    this.scheduleRepository = scheduleRepository;
    this.entityManager = entityManager;
  }


  @Override
  public List<Schedule> getAllSchedules() {
    return scheduleRepository.findAll();
  }

  @Override
  public Schedule getScheduleById(long id) {
    return scheduleRepository.getById(id);
  }

  @Override
  public List<Schedule> getScheduleBySubjectId(long subjectId) {
    String jpql = "SELECT s FROM Schedule s JOIN s.subject t WHERE t.subjectId = :subjectId";
    List<Schedule> schedules = entityManager.createQuery(jpql, Schedule.class)
        .setParameter("subjectId", subjectId)
        .getResultList();
    return schedules;
  }

  @Override
  public List<Schedule> getScheduleByTimetableId(long timetableId) {
    String jpql = "SELECT s FROM Schedule s JOIN s.timetable t WHERE t.timetableId = :timetableId";
    List<Schedule> schedules = entityManager.createQuery(jpql, Schedule.class)
        .setParameter("timetableId", timetableId)
        .getResultList();
    return schedules;
  }

  @Override
  public List<Schedule> getScheduleBySemesterId(long semesterId) {
    String jpql = "SELECT s FROM Schedule s JOIN s.semester t WHERE t.semesterId = :semesterId";
    List<Schedule> schedules = entityManager.createQuery(jpql, Schedule.class)
        .setParameter("semesterId", semesterId)
        .getResultList();
    return schedules;
  }

  @Override
  public Schedule addSchedule(Schedule schedule) {
    return scheduleRepository.save(schedule);
  }

  @Override
  public Schedule updateSchedule(Schedule schedule) {
    return scheduleRepository.saveAndFlush(schedule);
  }

  @Override
  public void deleteScheduleById(long id) {
    scheduleRepository.deleteById(id);
  }
}
