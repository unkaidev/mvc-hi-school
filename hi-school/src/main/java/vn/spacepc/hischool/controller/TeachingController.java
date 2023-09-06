package vn.spacepc.hischool.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.AttendanceRepository;
import vn.spacepc.hischool.dao.ScheduleRepository;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.dao.TeachingRepository;
import vn.spacepc.hischool.dao.TimetableRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Attendance;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Teaching;
import vn.spacepc.hischool.entity.Timetable;
import vn.spacepc.hischool.entity.Transcript;

@Controller
@RequestMapping("/teaching")

public class TeachingController {

  private TeachingRepository teachingRepository;
  private AccountRepository accountRepository;
  private AttendanceRepository attendanceRepository;

  @Autowired
  public TeachingController(TeachingRepository teachingRepository,
      AccountRepository accountRepository, AttendanceRepository attendanceRepository) {
    this.teachingRepository = teachingRepository;
    this.accountRepository = accountRepository;
    this.attendanceRepository = attendanceRepository;
  }


  @GetMapping("/list")
  public String showTeachingPage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Teacher teacher = account.getTeacher();
      if (teacher != null) {
        List<Teaching> teachings = teachingRepository.findByTeacher(teacher);
        List<Attendance> attendances = attendanceRepository.findByTeacher(teacher);
        model.addAttribute("teachings", teachings);
        model.addAttribute("attendances", attendances);

        return "teacher/teaching-list";
      }
    }
    return "home";
  }

  @GetMapping("/edit/{id}")
  public String showEditTeachingForm(@PathVariable("id") Long id, Model model) {
    Teaching teaching = teachingRepository.findById(id).orElse(null);
    if (teaching == null) {
      return "redirect:/teaching/list";
    }
    model.addAttribute("teaching", teaching);

    return "teacher/teaching-form";
  }

  @GetMapping("/delete/{id}")
  public String deleteTeaching(@PathVariable Long id) {
    teachingRepository.deleteById(id);

    return "redirect:/teaching/list";
  }

  @PostMapping("/save")
  public String saveTeaching(@ModelAttribute Teaching teaching) {
    teachingRepository.save(teaching);
    return "redirect:/teaching/list";
  }


  @GetMapping("/attendance-list")
  public String showAttendancePage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    System.out.println(username);
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Teacher teacher = account.getTeacher();
      if (teacher != null) {
        List<Attendance> attendances = attendanceRepository.findByTeacher(teacher);
        model.addAttribute("attendances", attendances);

        return "teacher/attendance-list";
      }
    }
    return "home";
  }

  @GetMapping("/attendance-edit/{id}")
  public String toggleAttendance(@PathVariable Long id) {
    Attendance attendance = attendanceRepository.findById(id).orElse(null);

    if (attendance != null) {
      attendance.setPresent(!attendance.isPresent());
      attendanceRepository.save(attendance);
    }

    return "redirect:/teaching/attendance-list";
  }

}
