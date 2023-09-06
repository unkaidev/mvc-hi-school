package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.SchoolRepository;
import vn.spacepc.hischool.entity.School;

@Service
public class SchoolServiceImpl implements SchoolService {

  private SchoolRepository schoolRepository;

  @Autowired

  public SchoolServiceImpl(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }


  @Override
  public List<School> getAllSchools() {
    return schoolRepository.findAll();
  }

  @Override
  public School getSchoolById(long id) {
    return schoolRepository.getById(id);
  }

  @Override
  @Transactional
  public School addSchool(School school) {
    return schoolRepository.save(school);
  }

  @Override
  @Transactional
  public School updateSchool(School school) {
    return schoolRepository.saveAndFlush(school);
  }

  @Override
  @Transactional
  public void deleteSchoolById(long id) {
    schoolRepository.deleteById(id);
  }
}
