package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Teacher;

public interface SchoolClassService {
  public List<SchoolClass> getAllSchoolClasss();
  public SchoolClass getSchoolClassById(long id);
  public List<SchoolClass> getSchoolClassByTeacherId(long teacherId);

  public List<SchoolClass> getSchoolClassBySchoolYearId(long schoolYearId);
  public SchoolClass addSchoolClass(SchoolClass schoolClass);
  public SchoolClass updateSchoolClass(SchoolClass schoolClass);
  public void deleteSchoolClassById(long id);
}
