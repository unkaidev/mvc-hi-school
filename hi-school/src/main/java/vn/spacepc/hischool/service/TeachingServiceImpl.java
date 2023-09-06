package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.TeachingRepository;
import vn.spacepc.hischool.entity.Teaching;

@Service
public class TeachingServiceImpl implements TeachingService {

  private TeachingRepository teachingRepository;

  @Autowired

  public TeachingServiceImpl(TeachingRepository teachingRepository) {
    this.teachingRepository = teachingRepository;
  }


  @Override
  public List<Teaching> getAllTeachings() {
    return teachingRepository.findAll();
  }

  @Override
  public Teaching getTeachingById(long id) {
    return teachingRepository.getById(id);
  }

  @Override
  @Transactional
  public Teaching addTeaching(Teaching teaching) {
    return teachingRepository.save(teaching);
  }

  @Override
  @Transactional
  public Teaching updateTeaching(Teaching teaching) {
    return teachingRepository.saveAndFlush(teaching);
  }

  @Override
  @Transactional
  public void deleteTeachingById(long id) {
    teachingRepository.deleteById(id);
  }
}
