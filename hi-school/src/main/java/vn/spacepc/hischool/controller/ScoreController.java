package vn.spacepc.hischool.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.ScoreRepository;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherAssignmentRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Attendance;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Semester;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.TeacherAssignment;
import vn.spacepc.hischool.entity.Teaching;
import vn.spacepc.hischool.service.SemesterService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.SubjectService;

@Controller
@RequestMapping("/score")
public class ScoreController {

  private SubjectRepository subjectRepository;

  private StudentRepository studentRepository;

  private SemesterRepository semesterRepository;
  private ScoreRepository scoreRepository;
  private AccountRepository accountRepository;
  private TeacherAssignmentRepository teacherAssignmentRepository;

  @Autowired
  public ScoreController(SubjectRepository subjectRepository, StudentRepository studentRepository,
      SemesterRepository semesterRepository, ScoreRepository scoreRepository,
      AccountRepository accountRepository,
      TeacherAssignmentRepository teacherAssignmentRepository) {
    this.subjectRepository = subjectRepository;
    this.studentRepository = studentRepository;
    this.semesterRepository = semesterRepository;
    this.scoreRepository = scoreRepository;
    this.accountRepository = accountRepository;
    this.teacherAssignmentRepository = teacherAssignmentRepository;
  }

  @GetMapping("list")
  public String showListScore(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Teacher teacher = account.getTeacher();
      if (teacher != null) {
        List<TeacherAssignment> teacherAssignments = teacherAssignmentRepository.findByTeacher(
            teacher);
        for (TeacherAssignment teacherAssignment : teacherAssignments
        ) {

          List<Score> scores = teacherAssignment.getScores();
          model.addAttribute("scores", scores);
        }

        return "teacher/score-list";
      }
    }
    return "home";
  }

  @GetMapping("/student/list")
  public String showListScoreForStudent(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Student student = account.getStudent();
      if (student != null) {
        List<Score> scores = student.getScores();
        model.addAttribute("scores", scores);

        return "teacher/score-list";
      }
    }
    return "home";
  }


  @GetMapping("/edit/{id}")
  public String showEditSubjectForm(@PathVariable("id") Long id, Model model) {
    Score score = scoreRepository.findById(id).orElse(null);
    if (score == null) {
      // Xử lý khi không tìm thấy subject
      return "redirect:/score/list";
    }

    List<Subject> subjectList = subjectRepository.findAll();
    List<Student> studentList = studentRepository.findAll();
    List<Semester> semesterList = semesterRepository.findAll();

    model.addAttribute("score", score);
    model.addAttribute("subjectList", subjectList);
    model.addAttribute("studentList", studentList);
    model.addAttribute("semesterList", semesterList);

    return "teacher/score-form";
  }

  @PostMapping("/save-scores")
  public String saveScores(@ModelAttribute Score score) {
    scoreRepository.save(score);

    return "redirect:/score/list";
  }

}

