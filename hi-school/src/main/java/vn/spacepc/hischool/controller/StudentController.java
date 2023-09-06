package vn.spacepc.hischool.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.ParentRepository;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.SchoolRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.TranscriptRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Parent;
import vn.spacepc.hischool.entity.Role;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import java.sql.Blob;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Transcript;
import vn.spacepc.hischool.service.SchoolClassService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TeacherService;


@Controller
@RequestMapping("/student")
public class StudentController {

  private StudentRepository studentRepository;
  private SchoolClassRepository schoolClassRepository;

  private ParentRepository parentRepository;

  private SchoolRepository schoolRepository;

  private TranscriptRepository transcriptRepository;
  private AccountRepository accountRepository;
  private StudentService studentService;
  private TeacherService teacherService;
  private SchoolClassService schoolClassService;

  @Autowired
  public StudentController(StudentRepository studentRepository,
      SchoolClassRepository schoolClassRepository, ParentRepository parentRepository,
      SchoolRepository schoolRepository, TranscriptRepository transcriptRepository,
      AccountRepository accountRepository, StudentService studentService,
      TeacherService teacherService, SchoolClassService schoolClassService) {
    this.studentRepository = studentRepository;
    this.schoolClassRepository = schoolClassRepository;
    this.parentRepository = parentRepository;
    this.schoolRepository = schoolRepository;
    this.transcriptRepository = transcriptRepository;
    this.accountRepository = accountRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
    this.schoolClassService = schoolClassService;
  }

  @GetMapping("/manager/list")
  public String showStudentListForManager(Model model) {
    List<Student> students = studentRepository.findAll();
    model.addAttribute("students", students);
    return "head-teacher/student-list";
  }

  @GetMapping("/head-teacher/list")
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
        for (SchoolClass schoolClass : schoolClasses) {
          List<Student> studentsInClass = studentService.getStudentsByClassId(
              schoolClass.getClassId());
          students.addAll(studentsInClass);
          for (Student student : students) {
            model.addAttribute("students", students);
          }
        }
        return "head-teacher/student-list";
      }
    }
    return "home";
  }

  @GetMapping("/student/list")
  public String showStudentListForStudent(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountRepository.findByUserName(username);
      Student student = account.getStudent();
      if (student != null) {
        model.addAttribute("students", student);
        return "head-teacher/student-list";
      }
    }
    return "home";
  }

  @GetMapping("/form")
  public String showStudentForm(Model model) {
    List<Transcript> allTranscripts = transcriptRepository.findAll();

    model.addAttribute("student", new Student());
    model.addAttribute("allClasses", schoolClassRepository.findAll());
    model.addAttribute("allParents", parentRepository.findAll());
    model.addAttribute("allSchools", schoolRepository.findAll());
    model.addAttribute("allTranscripts", allTranscripts);
    return "head-teacher/student-form";
  }

  @GetMapping("/edit/{id}")
  public String editStudent(@PathVariable Long id, Model model) {
    Student student = studentRepository.findById(id).orElse(null);
    model.addAttribute("student", student);
    model.addAttribute("allParents", parentRepository.findAll());
    model.addAttribute("allSchools", schoolRepository.findAll());
    model.addAttribute("allTranscripts", transcriptRepository.findAll());
    return "head-teacher/student-form";
  }

  @PostMapping("/save")
  public String saveStudent(@ModelAttribute Student student,
      @RequestParam("avatarFile") MultipartFile avatarFile,
      @ModelAttribute Email email, Principal principal,
      Model model) {
    try {
      // Kiểm tra studentId
      if (student.getStudentId() != null) {
        Student existingStudent = studentRepository.findById(student.getStudentId()).orElse(null);
        if (existingStudent != null) {
          updateStudent(existingStudent, student, avatarFile, email, model);
          return "head-teacher/student-form";
        }
      } else {
        createStudent(student, avatarFile, email, model);
        return "head-teacher/student-form";
      }
      return redirectToUserType(principal);

    } catch (IOException | SQLException e) {
      e.printStackTrace();
      return "redirect:/showPage400";
    }
  }

  private void updateStudent(Student existingStudent, Student updatedStudent,
      MultipartFile avatarFile,
      Email email, Model model) throws IOException, SQLException {
    if (!avatarFile.isEmpty()) {
      Blob avatarBlob = new SerialBlob(avatarFile.getBytes());
      existingStudent.setAvatar(avatarBlob);
    }

    if (existingStudent.getParent() == null) {
      Parent parent = new Parent();
      parent.setFullName("Parent of " + existingStudent.getFullName());
      existingStudent.setParent(parent);
    }

    existingStudent.setFullName(updatedStudent.getFullName());
    existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
    existingStudent.setNationality(updatedStudent.getNationality());
    existingStudent.setEthnicity(updatedStudent.getEthnicity());
    existingStudent.setCitizenId(updatedStudent.getCitizenId());
    existingStudent.setIssuedDate(updatedStudent.getIssuedDate());
    existingStudent.setIssuedPlace(updatedStudent.getIssuedPlace());
    existingStudent.setPermanentAddress(updatedStudent.getPermanentAddress());
    existingStudent.setContactAddress(updatedStudent.getContactAddress());

    Email oldEmail = existingStudent.getEmail();

    if (!email.equals(oldEmail)) {
      Teacher teacher = teacherService.findByEmail(email);
      Student studentByEmail = studentService.findByEmail(email);

      if (teacher == null && studentByEmail == null) {
        existingStudent.setEmail(email);
        studentRepository.save(existingStudent);
        model.addAttribute("successMessage", "Save success!");
      } else {
        model.addAttribute("errorMessageEdit", "Email already exists!");
      }
    } else {
      studentRepository.save(existingStudent);
      model.addAttribute("successMessage", "Save success!");
    }
  }


  private void createStudent(Student student, MultipartFile avatarFile, Email email, Model model)
      throws IOException, SQLException {
    Teacher teacher = teacherService.findByEmail(email);
    Student studentByEmail = studentService.findByEmail(email);


    if (teacher != null || studentByEmail != null) {
      model.addAttribute("errorMessageCreate", "Email already exists!");
      model.addAttribute("allParents", student.getParent());
      model.addAttribute("allSchools", student.getSchool());
      model.addAttribute("allTranscripts", student.getTranscripts());
      return;
    }

    Blob avatarBlob = new SerialBlob(avatarFile.getBytes());
    student.setAvatar(avatarBlob);

    Parent parent = new Parent();
    parent.setFullName("Parent of " + student.getFullName());
    student.setParent(parent);

    student.setEmail(email);
    studentRepository.save(student);

    model.addAttribute("successMessage", "Save success!");
  }


  private String redirectToUserType(Principal principal) {
    if (isHeadTeacher(principal)) {
      return "redirect:/student/head-teacher/list";
    } else if (isManager(principal)) {
      return "redirect:/student/manager/list";
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

  @GetMapping("/delete/{id}")
  public String deleteStudent(@PathVariable Long id, Principal principal) {
    studentRepository.deleteById(id);
    if (isHeadTeacher(principal)) {
      return "redirect:/student/head-teacher/list";
    } else if (isManager(principal)) {
      return "redirect:/student/manager/list";
    } else {
      return "redirect://checkUserRole";
    }
  }
}

