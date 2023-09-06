package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

  Role findByName(String name);
}