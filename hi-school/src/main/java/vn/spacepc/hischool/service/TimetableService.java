package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Timetable;

public interface TimetableService {
  public List<Timetable> getAllTimetables();
  public Timetable getTimetableById(long id);
  public List<Timetable> getTimetablesBySemesterId(long semesterId);

  List<Timetable> getTimetablesBySchoolClass(long schoolClassId);

  List<SchoolClass> getSchoolClassesByTimetable(long timetableId);

  public Timetable addTimetable(Timetable timetable);
  public Timetable updateTimetable(Timetable timetable);
  public void deleteTimetableById(long id);
}
