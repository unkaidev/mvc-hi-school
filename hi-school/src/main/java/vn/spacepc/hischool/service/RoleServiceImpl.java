package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.RoleRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Role;
@Service
public class RoleServiceImpl implements RoleService{


  private RoleRepository roleRepository;

  private EntityManager entityManager;
@Autowired
  public RoleServiceImpl(RoleRepository roleRepository, EntityManager entityManager) {
    this.roleRepository = roleRepository;
    this.entityManager = entityManager;
  }

  @Override
  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  @Override
  public List<Role> getRolesByAccountId(int accountId) {
    String jpql = "SELECT r FROM Role r JOIN r.account a WHERE a.id = :accountId";
    List<Role> roles = entityManager.createQuery(jpql, Role.class)
        .setParameter("accountId", accountId)
        .getResultList();
    return roles;
  }
  @Override
  @Transactional
  public Role addRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  @Transactional
  public Role updateRole(Role role) {
    return roleRepository.saveAndFlush(role);
  }

  @Override
  @Transactional
  public void deleteRoleById(int id) {
    List<Role> roles = this.getRolesByAccountId(id);
    if (roles != null && !roles.isEmpty()) {
      for (Role role : roles) {
        roleRepository.delete(role);
      }
    }
  }
}
