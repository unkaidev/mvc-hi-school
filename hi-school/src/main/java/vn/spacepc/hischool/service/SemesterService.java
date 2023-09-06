package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Semester;

public interface SemesterService {
  public List<Semester> getAllSemesters();
  public List<Semester> getSemestersBySchoolYearId(long id);
  public Semester getSemesterById(long id);
  public Semester addSemester(Semester semester);
  public Semester updateSemester(Semester semester);
  public void deleteSemesterById(long id);

}
