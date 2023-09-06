package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.ParentRepository;
import vn.spacepc.hischool.entity.Parent;
import vn.spacepc.hischool.entity.Transcript;

@Service
public class ParentServiceImpl implements ParentService {

  private ParentRepository parentRepository;
  private EntityManager entityManager;

  @Autowired
  public ParentServiceImpl(ParentRepository parentRepository, EntityManager entityManager) {
    this.parentRepository = parentRepository;
    this.entityManager = entityManager;
  }

  @Override
  public List<Parent> getAllParents() {
    return parentRepository.findAll();
  }

  @Override
  public Parent getParentById(long id) {
    return parentRepository.getById(id);
  }

  @Override
  @Transactional
  public Parent addParent(Parent parent) {
    return parentRepository.save(parent);
  }

  @Override
  @Transactional
  public Parent updateParent(Parent parent) {
    return parentRepository.saveAndFlush(parent);
  }

  @Override
  @Transactional
  public void deleteParentById(long id) {
    parentRepository.deleteById(id);
  }
}
