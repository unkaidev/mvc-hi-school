package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.SchoolYear;

public interface SchoolYearService {
  public List<SchoolYear> getAllSchoolYears();
  public SchoolYear getSchoolYearById(long id);
  public SchoolYear addSchoolYear(SchoolYear schoolYear);
  public SchoolYear updateSchoolYear(SchoolYear schoolYear);
  public void deleteSchoolYearById(long id);
}
