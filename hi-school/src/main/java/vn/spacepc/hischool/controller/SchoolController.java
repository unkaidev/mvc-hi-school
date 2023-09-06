package vn.spacepc.hischool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.spacepc.hischool.dao.SchoolRepository;
import vn.spacepc.hischool.dao.SchoolYearRepository;
import vn.spacepc.hischool.entity.School;
import vn.spacepc.hischool.entity.SchoolYear;

@Controller
@RequestMapping("/school")
public class SchoolController {

  private SchoolRepository schoolRepository;
  private SchoolYearRepository schoolYearRepository;

  @Autowired
  public SchoolController(SchoolRepository schoolRepository, SchoolYearRepository schoolYearRepository) {
    this.schoolRepository = schoolRepository;
    this.schoolYearRepository = schoolYearRepository;
  }

  // Mapping for showing the list of schools
  @GetMapping("/schools")
  public String showSchoolList(Model model) {
    Iterable<School> schools = schoolRepository.findAll();
    model.addAttribute("schools", schools);
    return "admin/school-list";
  }

  // Mapping for showing the form to add a new school
  @GetMapping("/school-form")
  public String showSchoolForm(Model model) {
    model.addAttribute("school", new School());
    return "admin/school-form";
  }

  // Mapping for saving a new school or updating an existing one
  @PostMapping("/saveSchool")
  public String saveSchool(@ModelAttribute School school) {
    schoolRepository.save(school);
    return "redirect:/school/schools";
  }
  @GetMapping("/schools/edit/{id}")
  public String editSchool(@PathVariable Long id, Model model) {
    School school = schoolRepository.findById(id).orElse(null);
    model.addAttribute("school", school);
    return "admin/school-form";
  }

  // Mapping for deleting an existing school
  @GetMapping("/schools/delete/{id}")
  public String deleteSchool(@PathVariable Long id) {
    schoolRepository.deleteById(id);
    return "redirect:/school/schools";
  }

  //SCHOOL YEAR CONTROLLER
  // Mapping for showing the list of school years
  @GetMapping("/school-years")
  public String showSchoolYearList(Model model) {
    Iterable<SchoolYear> schoolYears = schoolYearRepository.findAll();
    model.addAttribute("schoolYears", schoolYears);
    return "manager/school-year-list";
  }

  // Mapping for showing the form to add a new school year
  @GetMapping("/school-year-form")
  public String showSchoolYearForm(Model model) {
    model.addAttribute("schoolYear", new SchoolYear());
    return "manager/school-year-form";
  }

  // Mapping for saving a new school year or updating an existing one
  @PostMapping("/saveSchoolYear")
  public String saveSchoolYear(@ModelAttribute SchoolYear schoolYear) {
    schoolYearRepository.save(schoolYear);
    return "redirect:/school/school-years";
  }

  // Mapping for showing the form to edit an existing school year
  @GetMapping("/school-years/edit/{id}")
  public String editSchoolYear(@PathVariable Long id, Model model) {
    SchoolYear schoolYear = schoolYearRepository.findById(id).orElse(null);
    model.addAttribute("schoolYear", schoolYear);
    return "manager/school-year-form";
  }

  // Mapping for deleting an existing school year
  @GetMapping("/school-years/delete/{id}")
  public String deleteSchoolYear(@PathVariable Long id) {
    schoolYearRepository.deleteById(id);
    return "redirect:/school/school-years";
  }
}

