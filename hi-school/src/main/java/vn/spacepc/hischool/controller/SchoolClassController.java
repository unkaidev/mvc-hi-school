package vn.spacepc.hischool.controller;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.SchoolYearRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.dao.TranscriptRepository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Transcript;
import vn.spacepc.hischool.service.TranscriptService;

@Controller
@RequestMapping("/school-class")
public class SchoolClassController {

  private SchoolClassRepository schoolClassRepository;
  private SchoolYearRepository schoolYearRepository;
  private TeacherRepository teacherRepository;
  private StudentRepository studentRepository;
  private TranscriptRepository transcriptRepository;
  private TranscriptService transcriptService;

  @Autowired

  public SchoolClassController(SchoolClassRepository schoolClassRepository,
      SchoolYearRepository schoolYearRepository, TeacherRepository teacherRepository,
      StudentRepository studentRepository, TranscriptRepository transcriptRepository,
      TranscriptService transcriptService) {
    this.schoolClassRepository = schoolClassRepository;
    this.schoolYearRepository = schoolYearRepository;
    this.teacherRepository = teacherRepository;
    this.studentRepository = studentRepository;
    this.transcriptRepository = transcriptRepository;
    this.transcriptService = transcriptService;
  }


  @GetMapping("/school-classes")
  public String showSchoolClassList(Model model) {
    Iterable<SchoolClass> schoolClasses = schoolClassRepository.findAll();
    model.addAttribute("schoolClasses", schoolClasses);
    return "manager/school-class-list";
  }

  @GetMapping("/school-classes-form")
  public String showSchoolClassForm(Model model) {
    model.addAttribute("schoolClass", new SchoolClass());
    model.addAttribute("allSchoolYears", schoolYearRepository.findAll());
    model.addAttribute("allTeachers", teacherRepository.findAll());
    model.addAttribute("allStudents", studentRepository.findAll());
    return "manager/school-class-form";
  }

  @PostMapping("/saveSchoolClass")
  public String saveSchoolClass(@ModelAttribute SchoolClass schoolClass,
      @ModelAttribute SchoolYear schoolYear, Model model) {
    // Check the uniqueness of the class name within the same academic year.
    List<SchoolClass> existingClasses = schoolClassRepository.findByClassNameAndSchoolYear(
        schoolClass.getClassName(), schoolClass.getSchoolYear());
    if (!existingClasses.isEmpty()) {
      model.addAttribute("error", "The class name already exists in this academic year.");
      return "manager/school-class-form";
    }

    // Check if the student has already enrolled in another class within the same academic year.

    for (Student student : schoolClass.getStudents()) {

      List<SchoolClass> studentClasses = schoolClassRepository.findBySchoolYearAndStudentsContaining(
          schoolClass.getSchoolYear(), student);
      if (!studentClasses.isEmpty()) {
        model.addAttribute("error", "The student has already enrolled in another class within this academic year.");
        return "manager/school-class-form";
      }else {
        Transcript transcript = new Transcript();
        transcript.setStudent(student);
        transcript.setSchoolYear(schoolYear);
        transcriptRepository.save(transcript);
      }
    }
    schoolClassRepository.save(schoolClass);
    return "redirect:/school-class/school-classes";
  }

  @GetMapping("/school-year-selection")
  public String showSchoolYearSelection(Model model) {
    List<SchoolYear> allSchoolYears = schoolYearRepository.findAll();
    model.addAttribute("allSchoolYears", allSchoolYears);
    return "manager/school-year-selection";
  }

  @PostMapping("/selectSchoolYear")
  public String selectSchoolYear(@RequestParam SchoolYear selectedSchoolYear, HttpSession session) {
    session.setAttribute("selectedSchoolYear", selectedSchoolYear);
    return "redirect:/school-class/add";
  }

  @GetMapping("/add")
  public String showSchoolClassForm(@ModelAttribute SchoolClass schoolClass, Model model,
      HttpSession session) {
    SchoolYear selectedSchoolYear = (SchoolYear) session.getAttribute("selectedSchoolYear");
    List<Student> allStudents = studentRepository.findAll();
    List<Teacher> allTeachers = teacherRepository.findAll();

    List<SchoolClass> allSchoolClasses = schoolClassRepository.findBySchoolYear(selectedSchoolYear);
    List<Student> studentsNotInClass = new ArrayList<>(allStudents);
    List<Teacher> teachersNotInClass = new ArrayList<>(allTeachers);

    for (SchoolClass existingClass : allSchoolClasses) {
      studentsNotInClass.removeAll(existingClass.getStudents());
      teachersNotInClass.remove(existingClass.getTeacher());
    }

    model.addAttribute("schoolClass", schoolClass);
    model.addAttribute("allSchoolYears", selectedSchoolYear);
    model.addAttribute("studentsNotInClass", studentsNotInClass);
    model.addAttribute("teachersNotInClass", teachersNotInClass);

    return "manager/school-class-form";
  }


  @GetMapping("/school-classes/edit/{id}")
  public String editSchoolClass(@PathVariable Long id, Model model) {
    SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);

    if (schoolClass == null) {
      return "redirect:/showPage400";
    }

    List<Student> allStudents = studentRepository.findAll();

    List<Student> studentsNotInClass = new ArrayList<>(allStudents);

    List<SchoolClass> allSchoolClassesInSemester = schoolClassRepository.findBySchoolYear(
        schoolClass.getSchoolYear());

    for (SchoolClass existingClass : allSchoolClassesInSemester) {
      studentsNotInClass.removeAll(existingClass.getStudents());

    }

    model.addAttribute("schoolClass", schoolClass);
    model.addAttribute("allSchoolYears", schoolYearRepository.findAll());
    model.addAttribute("currentStudents", schoolClass.getStudents());
    model.addAttribute("studentsNotInClass", studentsNotInClass);
    model.addAttribute("teachersNotInClass", schoolClass.getTeacher());

    return "manager/school-class-form-3";
  }

  @GetMapping("/school-classes/edit-current/{id}")
  public String editListSchoolClass(@PathVariable Long id, Model model) {
    SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);

    model.addAttribute("schoolClass", schoolClass);
    model.addAttribute("allSchoolYears", schoolYearRepository.findAll());
    model.addAttribute("currentStudents", schoolClass.getStudents());
    model.addAttribute("teachersInClass", schoolClass.getTeacher());

    return "manager/school-class-form-2";
  }

  @PostMapping("/save-edit")
  public String saveOrEditSchoolClass(@ModelAttribute SchoolClass schoolClass,
      @RequestParam(name = "students", required = false) List<Long> studentsIds,
      Model model) {
    SchoolClass existingClass = schoolClassRepository.findById(schoolClass.getClassId())
        .orElse(null);

    if (existingClass == null) {
      return "redirect:/showPage400";
    }

    existingClass.setClassName(schoolClass.getClassName());
    existingClass.setTeacher(schoolClass.getTeacher());
    existingClass.setSchoolYear(schoolClass.getSchoolYear());

    if (studentsIds != null) {
      List<Student> studentsToAdd = studentRepository.findAllById(studentsIds);

      existingClass.getStudents().addAll(studentsToAdd);
      schoolClassRepository.save(existingClass);

      for (Student student : studentsToAdd) {
        createTranscriptForStudentInClass(student, existingClass);
      }
    }

    return "redirect:/school-class/school-classes";
  }

  private void createTranscriptForStudentInClass(Student student, SchoolClass schoolClass) {
    Set<SchoolYear> schoolYearsInClass = new HashSet<>();
    for (SchoolClass sc : student.getSchoolClasses()) {
      if (sc.equals(schoolClass)) {
        schoolYearsInClass.add(sc.getSchoolYear());
      }
    }

    for (SchoolYear schoolYear : schoolYearsInClass) {
      List<Transcript> existingTranscripts = transcriptRepository.findByStudentAndSchoolYear(
          student, schoolYear);
      if (existingTranscripts.isEmpty()) {
        Transcript yearTranscript = new Transcript();
        yearTranscript.setStudent(student);
        yearTranscript.setSchoolYear(schoolYear);
        student.getTranscripts().add(yearTranscript);
        transcriptRepository.save(yearTranscript);
      }
    }
    studentRepository.save(student);
  }


  @PostMapping("/save-edit-remove")
  public String saveRemoveSchoolClass(@ModelAttribute SchoolClass schoolClass,
      @RequestParam(name = "currentStudents", required = false) List<Long> currentStudentIds,
      Model model) {
    SchoolClass existingClass = schoolClassRepository.findById(schoolClass.getClassId())
        .orElse(null);

    if (existingClass == null) {
      return "redirect:/showPage400";
    }

    if (currentStudentIds != null && !currentStudentIds.isEmpty()) {
      List<Student> currentStudents = studentRepository.findAllById(currentStudentIds);
      existingClass.getStudents().removeAll(currentStudents);

      for (Student student : currentStudents) {
        removeTranscriptForStudentInClass(student, existingClass.getSchoolYear());
      }
    }

    schoolClassRepository.save(existingClass);
    return "redirect:/school-class/school-classes";
  }

  private void removeTranscriptForStudentInClass(Student student, SchoolYear schoolYear) {
    List<Transcript> transcripts = transcriptRepository.findByStudentAndSchoolYear(student,
        schoolYear);
    for (Transcript transcript : transcripts) {
      transcript.setStudent(null);
      transcriptRepository.save(transcript);
      transcriptRepository.delete(transcript);
    }
  }


  @GetMapping("/school-classes/delete/{id}")
  public String deleteSchoolClass(@PathVariable Long id) {

    Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
    for (Student student:  schoolClass.get().getStudents()
    ) {
      List<Transcript> transcripts = transcriptRepository.findByStudentAndSchoolYear(student,schoolClass.get().getSchoolYear());
      for (Transcript transcript: transcripts
      ) {
        transcriptRepository.deleteById(transcript.getTranscriptId());
      }
    }
    schoolClassRepository.deleteById(schoolClass.get().getClassId());
    return "redirect:/school-class/school-classes";
  }
}
