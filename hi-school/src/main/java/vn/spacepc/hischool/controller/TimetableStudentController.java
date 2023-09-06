package vn.spacepc.hischool.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.TimetableRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Attendance;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Teaching;
import vn.spacepc.hischool.entity.Timetable;
import vn.spacepc.hischool.service.SchoolClassService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TeacherService;
import vn.spacepc.hischool.service.TimetableService;

@Controller
@RequestMapping("/timetable-student")
public class TimetableStudentController {

  private AccountRepository accountRepository;
  private StudentService studentService;
  private TeacherService teacherService;
  private StudentRepository studentRepository;
  private TimetableRepository timetableRepository;
  private TimetableService timetableService;
  private SchoolClassService schoolClassService;

  @Autowired
  public TimetableStudentController(AccountRepository accountRepository,
      StudentService studentService, TeacherService teacherService,
      StudentRepository studentRepository, TimetableRepository timetableRepository,
      TimetableService timetableService, SchoolClassService schoolClassService) {
    this.accountRepository = accountRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
    this.studentRepository = studentRepository;
    this.timetableRepository = timetableRepository;
    this.timetableService = timetableService;
    this.schoolClassService = schoolClassService;
  }


  @GetMapping("/list")
  public String showTimetableList(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      Account account = accountRepository.findByUserName(username);

      if (account != null && account.getStudent() != null) {
        Student student = account.getStudent();
        List<Timetable> studentTimetables = new ArrayList<>();

        List<Timetable> timetables = timetableService.getAllTimetables();
        for (Timetable timetable : timetables) {
          List<SchoolClass> schoolClasses = timetableService.getSchoolClassesByTimetable(timetable.getTimetableId());

          for (SchoolClass schoolClass : schoolClasses) {
            List<Student> students = studentService.getStudentsByClassId(schoolClass.getClassId());

            for (Student student1 : students) {
              if (student1.getStudentId().equals(student.getStudentId())) {
                studentTimetables.add(timetable);
                break;
              }
            }
          }
        }

        model.addAttribute("timetables", studentTimetables);
        return "student/timetable-list";
      }
    }

    return "home";
  }


}
