package vn.spacepc.hischool.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.service.AccountService;

@Controller
public class LoginController {

  private AccountService accountService;

  @Autowired
  public LoginController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/showLoginPage")
  public String showLoginPage(@RequestParam(name = "error", required = false) String error, Model model) {
    if (error != null && error.equals("InvalidCredentials")) {
      model.addAttribute("errorMessage", "Invalid username or password");
    } else if (error != null && error.equals("AccountIsNull")) {
      model.addAttribute("errorMessage", "Account is null");
    }
    return "login";
  }

  @GetMapping("/home")
  public String showHomePage() {

    return "home";
  }

  @GetMapping("/showPage403")
  public String showPage403() {
    return "error/403";
  }
  @GetMapping("/showPage400")
  public String showPage400() {
    return "error/400";
  }

  @GetMapping("/checkUserRole")
  public String checkUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      for (GrantedAuthority authority : authentication.getAuthorities()) {
        if (authority.getAuthority().equals("ROLE_ADMIN")) {
          return "admin/admin";
        } else if (authority.getAuthority().equals("ROLE_MANAGER")) {
          return "manager/manager";
        } else if (authority.getAuthority().equals("ROLE_HEADTEACHER")) {
          return "head-teacher/head-teacher";
        } else if (authority.getAuthority().equals("ROLE_TEACHER")) {
          return "teacher/teacher";
        } else if (authority.getAuthority().equals("ROLE_STUDENT")) {
          return "student/student";
        } else {
          return "redirect:/home";
        }
      }
    }
    return "redirect:/home";
  }

}
