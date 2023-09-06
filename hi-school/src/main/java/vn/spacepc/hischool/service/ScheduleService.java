package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Schedule;

public interface ScheduleService {
  public List<Schedule> getAllSchedules();
  public Schedule getScheduleById(long id);
  public List<Schedule> getScheduleBySubjectId(long subjectId);
  public List<Schedule> getScheduleByTimetableId(long timetableId);
  public List<Schedule> getScheduleBySemesterId(long semesterId);
  public Schedule addSchedule(Schedule schedule);
  public Schedule updateSchedule(Schedule schedule);
  public void deleteScheduleById(long id);
}
