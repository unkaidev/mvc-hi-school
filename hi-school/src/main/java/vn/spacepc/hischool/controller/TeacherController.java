package vn.spacepc.hischool.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.spacepc.hischool.dao.ScoreRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

  private TeacherRepository teacherRepository;
  private StudentService studentService;
  private TeacherService teacherService;

  @Autowired

  public TeacherController(TeacherRepository teacherRepository, StudentService studentService,
      TeacherService teacherService) {
    this.teacherRepository = teacherRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
  }


  @GetMapping("/teachers")
  public String showTeacherList(Model model) {
    Iterable<Teacher> teachers = teacherRepository.findAll();
    model.addAttribute("teachers", teachers);
    return "manager/teacher-list";
  }

  @GetMapping("/teachers-form")
  public String showTeacherForm(Model model) {
    model.addAttribute("teacher", new Teacher());
    return "manager/teacher-form";
  }

  @PostMapping("/saveTeacher")
  public String saveTeacher(@ModelAttribute Teacher teacher,
      @ModelAttribute Email email, Model model) {
    Student student = studentService.findByEmail(email);
    Teacher teacherByEmail = teacherService.findByEmail(email);

    if (student == null && teacherByEmail == null) {
      teacher.setEmail(email);
      teacherRepository.save(teacher);
      return "redirect:/teacher/teachers";
    } else {
      model.addAttribute("errorMessage", "Email already exists!");
      return "manager/teacher-form";
    }
  }


  @GetMapping("/teachers/edit/{id}")
  public String editTeacher(@PathVariable Long id, Model model) {
    Teacher teacher = teacherRepository.findById(id).orElse(null);
    model.addAttribute("teacher", teacher);
    return "manager/teacher-form";
  }

  @GetMapping("/teachers/delete/{id}")
  public String deleteTeacher(@PathVariable Long id) {
    teacherRepository.deleteById(id);
    return "redirect:/teacher/teachers";
  }


}

