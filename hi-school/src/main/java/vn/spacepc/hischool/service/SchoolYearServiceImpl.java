package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.SchoolYearRepository;
import vn.spacepc.hischool.entity.SchoolYear;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {

  private SchoolYearRepository schoolYearRepository;

  @Autowired

  public SchoolYearServiceImpl(SchoolYearRepository schoolYearRepository) {
    this.schoolYearRepository = schoolYearRepository;
  }


  @Override
  public List<SchoolYear> getAllSchoolYears() {
    return schoolYearRepository.findAll();
  }

  @Override
  public SchoolYear getSchoolYearById(long id) {
    return schoolYearRepository.getById(id);
  }

  @Override
  @Transactional
  public SchoolYear addSchoolYear(SchoolYear schoolYear) {
    return schoolYearRepository.save(schoolYear);
  }

  @Override
  @Transactional
  public SchoolYear updateSchoolYear(SchoolYear schoolYear) {
    return schoolYearRepository.saveAndFlush(schoolYear);
  }

  @Override
  @Transactional
  public void deleteSchoolYearById(long id) {
    schoolYearRepository.deleteById(id);
  }
}
