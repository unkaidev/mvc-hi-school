package vn.spacepc.hischool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.spacepc.hischool.dao.RoleRepository;
import vn.spacepc.hischool.entity.Role;

@Controller
@RequestMapping("/role")
public class RoleController {

  private RoleRepository roleRepository;

  @Autowired
  public RoleController(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @GetMapping("/list")
  public String listRoles(Model model) {
    Iterable<Role> roles = roleRepository.findAll();
    model.addAttribute("roles", roles);
    return "admin/role-list";
  }

  @GetMapping("/create")
  public String showRoleForm(Model model) {
    model.addAttribute("role", new Role());
    return "admin/role-form";
  }

  @PostMapping("/save")
  public String saveRole(@ModelAttribute Role role) {
    roleRepository.save(role);
    return "redirect:/role/list";
  }

  @GetMapping("/edit/{id}")
  public String editRole(@PathVariable Long id, Model model) {
    Role role = roleRepository.findById(id).orElse(null);
    model.addAttribute("role", role);
    return "admin/role-form";
  }

  @GetMapping("/delete/{id}")
  public String deleteRole(@PathVariable Long id) {
    roleRepository.deleteById(id);
    return "redirect:/role/list";
  }
}
