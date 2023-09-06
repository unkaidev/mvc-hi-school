package vn.spacepc.hischool.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.ParentRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Parent;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Transcript;
import vn.spacepc.hischool.service.ParentService;
import vn.spacepc.hischool.service.SchoolClassService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TeacherService;

@Controller
@RequestMapping("/parent")
public class ParentController {

  private ParentRepository parentRepository;
  private StudentRepository studentRepository;
  private AccountRepository accountRepository;
  private StudentService studentService;
  private ParentService parentService;
  private SchoolClassService schoolClassService;

  @Autowired
  public ParentController(ParentRepository parentRepository, StudentRepository studentRepository,
      AccountRepository accountRepository, StudentService studentService,
      ParentService parentService, SchoolClassService schoolClassService) {
    this.parentRepository = parentRepository;
    this.studentRepository = studentRepository;
    this.accountRepository = accountRepository;
    this.studentService = studentService;
    this.parentService = parentService;
    this.schoolClassService = schoolClassService;
  }
  @GetMapping("/manager/parents-list")
  public String showParentsList(Model model) {
    Iterable<Parent> parents = parentRepository.findAll();
    model.addAttribute("parents", parents);
    return "head-teacher/parent-list";
  }

  @GetMapping("/head-teacher/parents-list")
  public String showStudentListForHeadTeacher(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Teacher teacher = account.getTeacher();
      if (teacher != null) {
        List<SchoolClass> schoolClasses = schoolClassService.getSchoolClassByTeacherId(
            teacher.getTeacherId());
        List<Student> students = new ArrayList<>();
        List<Parent> parents = new ArrayList<>();
        for (SchoolClass schoolClass : schoolClasses) {
          List<Student> studentsInClass = studentService.getStudentsByClassId(
              schoolClass.getClassId());
          students.addAll(studentsInClass);
        }
        for (Student student : students) {
          parents.add(student.getParent());
        }
        model.addAttribute("parents", parents);
        return "head-teacher/parent-list";
      }
    }
    return "home";
  }


  @GetMapping("/student/parents-list")
  public String showStudentListForStudent(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Student student = account.getStudent();
      if (student != null) {
        Parent parent = student.getParent();
        model.addAttribute("parents", parent);
        return "head-teacher/parent-list";
      }
    }
    return "home";
  }


  @GetMapping("/parents-form")
  public String showParentForm(Model model) {
    List<Student> allStudents = studentRepository.findAll();
    Parent parent = new Parent(); // Create a new Parent instance
    model.addAttribute("parent", parent);
    model.addAttribute("allStudents", allStudents);
    return "head-teacher/parent-form";
  }

  @PostMapping("/saveParent")
  public String saveParent(@ModelAttribute Parent parent, Principal principal,
      @RequestParam(name = "studentIds", required = false) List<Long> studentIds) {
    if (studentIds != null && !studentIds.isEmpty()) {
      List<Student> students = studentRepository.findAllById(studentIds);
      parent.setStudents(students);

      for (Student student : students) {
        student.setParent(parent);
      }
    } else {
      parent.getStudents().clear();
    }

    parentRepository.save(parent);
    if (isHeadTeacher(principal)) {
      return "redirect:/parent/head-teacher/parents-list";
    } else if (isManager(principal)) {
      return "redirect:/parent/manager/parents-list";
    } else {
      return "redirect:/checkUserRole";
    }
  }
  private boolean isHeadTeacher(Principal principal) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      for (GrantedAuthority authority : authentication.getAuthorities()) {
        if ("ROLE_HEADTEACHER".equals(authority.getAuthority())) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isManager(Principal principal) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      for (GrantedAuthority authority : authentication.getAuthorities()) {
        if ("ROLE_MANAGER".equals(authority.getAuthority())) {
          return true;
        }
      }
    }
    return false;
  }


  @GetMapping("/edit-parent/{id}")
  public String editParent(@PathVariable Long id, Model model) {
    Parent parent = parentRepository.findById(id).orElse(null);
    List<Student> students = parent.getStudents();
    model.addAttribute("parent", parent);
    model.addAttribute("allStudents", students);
    return "head-teacher/parent-form";
  }

  @GetMapping("/delete-parent/{id}")
  public String deleteParent(@PathVariable Long id,Principal principal) {
    parentRepository.deleteById(id);
    if (isHeadTeacher(principal)) {
      return "redirect:/parent/head-teacher/parents-list";
    } else if (isManager(principal)) {
      return "redirect:/parent/manager/parents-list";
    } else {
      return "redirect:/checkUserRole";
    }
  }
}


