package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.School;

public interface SchoolService {
  public List<School> getAllSchools();
  public School getSchoolById(long id);
  public School addSchool(School school);
  public School updateSchool(School school);
  public void deleteSchoolById(long id);
}
