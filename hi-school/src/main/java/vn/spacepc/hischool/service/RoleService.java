package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Role;

public interface RoleService {
  public List<Role> getAllRoles();
  public List<Role> getRolesByAccountId(int id);
  public Role addRole(Role role);
  public Role updateRole(Role role);
  public void deleteRoleById(int id);

}
