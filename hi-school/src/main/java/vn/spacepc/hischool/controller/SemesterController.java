package vn.spacepc.hischool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.spacepc.hischool.dao.SchoolYearRepository;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.entity.Semester;

@Controller
@RequestMapping("/semester")
public class SemesterController {

  private SemesterRepository semesterRepository;
  private SchoolYearRepository schoolYearRepository;

  @Autowired
  public SemesterController(SemesterRepository semesterRepository, SchoolYearRepository schoolYearRepository) {
    this.semesterRepository = semesterRepository;
    this.schoolYearRepository = schoolYearRepository;
  }

  @GetMapping("/semesters")
  public String showSemesterList(Model model) {
    Iterable<Semester> semesters = semesterRepository.findAll();
    model.addAttribute("semesters", semesters);
    return "manager/semester-list";
  }

  @GetMapping("/semesters-form")
  public String showSemesterForm(Model model) {
    model.addAttribute("semester", new Semester());
    model.addAttribute("allSchoolYears", schoolYearRepository.findAll());
    return "manager/semester-form";
  }

  @PostMapping("/saveSemester")
  public String saveSemester(@ModelAttribute Semester semester) {
    semesterRepository.save(semester);
    return "redirect:/semester/semesters";
  }

  @GetMapping("/semesters/edit/{id}")
  public String editSemester(@PathVariable Long id, Model model) {
    Semester semester = semesterRepository.findById(id).orElse(null);
    model.addAttribute("semester", semester);
    model.addAttribute("allSchoolYears", schoolYearRepository.findAll());
    return "manager/semester-form";
  }

  @GetMapping("/semesters/delete/{id}")
  public String deleteSemester(@PathVariable Long id) {
    semesterRepository.deleteById(id);
    return "redirect:/semester/semesters";
  }
}

