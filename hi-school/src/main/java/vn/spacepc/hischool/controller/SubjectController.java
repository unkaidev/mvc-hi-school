package vn.spacepc.hischool.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Semester;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;

@Controller
@RequestMapping("/subject")
public class SubjectController {

  private SubjectRepository subjectRepository;
  private SemesterRepository semesterRepository;

  @Autowired
  public SubjectController(SubjectRepository subjectRepository,
      SemesterRepository semesterRepository) {
    this.subjectRepository = subjectRepository;
    this.semesterRepository = semesterRepository;

  }

  @GetMapping("/list")
  public String listSubjects(Model model) {
    List<Subject> subjects = subjectRepository.findAll();
    model.addAttribute("subjects", subjects);
    return "manager/subject-list";
  }

  @GetMapping("/add")
  public String showAddSubjectForm(Model model) {
    List<Semester> allSemesters = semesterRepository.findAll();

    model.addAttribute("allSemesters", allSemesters);
    model.addAttribute("subject", new Subject());

    return "manager/subject-form";
  }

  @GetMapping("/edit/{id}")
  public String showEditSubjectForm(@PathVariable("id") Long id, Model model) {
    Subject subject = subjectRepository.findById(id).orElse(null);
    if (subject == null) {
      // Xử lý khi không tìm thấy subject
      return "redirect:/subject/list";
    }

    List<Semester> allSemesters = semesterRepository.findAll();

    model.addAttribute("allSemesters", allSemesters);
    model.addAttribute("subject", subject);

    return "manager/subject-form";
  }

  @PostMapping("/save")
  public String saveSubject(@ModelAttribute Subject subject) {
    subjectRepository.save(subject);
    return "redirect:/subject/list";
  }

  @GetMapping("/delete/{id}")
  public String deleteSubject(@PathVariable("id") Long id) {
    subjectRepository.deleteById(id);
    return "redirect:/subject/list";
  }
}


