package vn.spacepc.hischool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.ScoreRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherAssignmentRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.TeacherAssignment;

@Controller
@RequestMapping("/teacher-assignment")
public class TeacherAssignmentController {

  private TeacherAssignmentRepository teacherAssignmentRepository;
  private SchoolClassRepository schoolClassRepository;
  private TeacherRepository teacherRepository;
  private SubjectRepository subjectRepository;
  private ScoreRepository scoreRepository;

  @Autowired

  public TeacherAssignmentController(TeacherAssignmentRepository teacherAssignmentRepository,
      SchoolClassRepository schoolClassRepository, TeacherRepository teacherRepository,
      SubjectRepository subjectRepository, ScoreRepository scoreRepository) {
    this.teacherAssignmentRepository = teacherAssignmentRepository;
    this.schoolClassRepository = schoolClassRepository;
    this.teacherRepository = teacherRepository;
    this.subjectRepository = subjectRepository;
    this.scoreRepository = scoreRepository;
  }

  @GetMapping("/list")
  public String showListTeacherAssignment(Model model) {
    List<TeacherAssignment> teacherAssignments = teacherAssignmentRepository.findAll();
    model.addAttribute("teacherAssignments", teacherAssignments);
    return "manager/teacher-assignment-list";
  }

  @GetMapping("/add")
  public String showCreateForm(Model model) {
    List<Teacher> allTeachers = teacherRepository.findAll();
    List<Subject> allSubjects = subjectRepository.findAll();
    List<SchoolClass> allClasses = schoolClassRepository.findAll();

    TeacherAssignment teacherAssignment = new TeacherAssignment();
    model.addAttribute("teacherAssignment", teacherAssignment);
    model.addAttribute("allTeachers", allTeachers);
    model.addAttribute("allSubjects", allSubjects);
    model.addAttribute("allClasses", allClasses);

    return "manager/teacher-assignment-form";
  }

  @PostMapping("/save")
  public String editTeacherAssignment(@ModelAttribute TeacherAssignment teacherAssignment) {

    teacherAssignmentRepository.save(teacherAssignment);

    Teacher teacher = teacherAssignment.getTeacher();
    Subject subject = teacherAssignment.getSubject();
    List<SchoolClass> schoolClasses = teacherAssignment.getClasses();

    List<Student> students = new ArrayList<>();
    for (SchoolClass schoolClass : schoolClasses) {
      students.addAll(schoolClass.getStudents());
    }

    List<Score> existingScores = scoreRepository.findByTeacherAssignment(teacherAssignment);

    for (Student student : students) {
      boolean scoreExists = false;

      for (Score existingScore : existingScores) {
        if (existingScore.getStudent().equals(student)) {
          scoreExists = true;
          break;
        }
      }

      if (!scoreExists) {
        Score score = new Score();
        score.setTeacherAssignment(teacherAssignment);
        score.setStudent(student);
        score.setSubject(subject);
        score.setSemester(subject.getSemester());
        score.setDailyScore(0.0); // Điểm số mặc định
        score.setMidtermScore(0.0); // Điểm số mặc định
        score.setFinalScore(0.0); // Điểm số mặc định
        score.setSubjectEvaluation(""); // Giá trị mặc định

        scoreRepository.save(score);
      }
    }

    return "redirect:/teacher-assignment/list";
  }


  @GetMapping("/edit/{id}")
  public String updateTeacherAssignment(@PathVariable Long id, Model model) {
    TeacherAssignment teacherAssignment = teacherAssignmentRepository.findById(id).orElse(null);
    if (teacherAssignment == null) {
      return "redirect:/teacher-assignment/list";
    }

    List<Teacher> allTeachers = teacherRepository.findAll();
    List<Subject> allSubjects = subjectRepository.findAll();
    List<SchoolClass> allClasses = schoolClassRepository.findAll();
    model.addAttribute("teacherAssignment", teacherAssignment);
    model.addAttribute("allTeachers", allTeachers);
    model.addAttribute("allSubjects", allSubjects);
    model.addAttribute("allClasses", allClasses);

    return "manager/teacher-assignment-form";
  }

  @GetMapping("/delete/{id}")
  public String deleteTeacherAssignment(@PathVariable Long id) {
    Optional<TeacherAssignment> teacherAssignment = teacherAssignmentRepository.findById(id);

    if (teacherAssignment.isPresent()) {
      List<Score> scoresToDelete = scoreRepository.findByTeacherAssignment(teacherAssignment.get());
      for (Score score : scoresToDelete) {
        scoreRepository.delete(score);
      }

      teacherAssignmentRepository.deleteById(id);

      return "redirect:/teacher-assignment/list";
    } else {
      return "redirect:/teacher-assignment/list";
    }
  }

}
