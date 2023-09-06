package vn.spacepc.hischool.controller;

import jakarta.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.spacepc.hischool.dao.AttendanceRepository;
import vn.spacepc.hischool.dao.ScheduleRepository;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.SemesterRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.dao.TeachingRepository;
import vn.spacepc.hischool.dao.TimetableRepository;
import vn.spacepc.hischool.entity.Attendance;
import vn.spacepc.hischool.entity.Schedule;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Semester;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Teaching;
import vn.spacepc.hischool.entity.Timetable;

@Controller
public class TimetableController {

  private TimetableRepository timetableRepository;
  private ScheduleRepository scheduleRepository;
  private SemesterRepository semesterRepository;
  private SubjectRepository subjectRepository;
  private TeacherRepository teacherRepository;
  private SchoolClassRepository schoolClassRepository;
  private TeachingRepository teachingRepository;
  private AttendanceRepository attendanceRepository;

  @Autowired
  public TimetableController(TimetableRepository timetableRepository,
      ScheduleRepository scheduleRepository, SemesterRepository semesterRepository,
      SubjectRepository subjectRepository, TeacherRepository teacherRepository,
      SchoolClassRepository schoolClassRepository, TeachingRepository teachingRepository,
      AttendanceRepository attendanceRepository) {
    this.timetableRepository = timetableRepository;
    this.scheduleRepository = scheduleRepository;
    this.semesterRepository = semesterRepository;
    this.subjectRepository = subjectRepository;
    this.teacherRepository = teacherRepository;
    this.schoolClassRepository = schoolClassRepository;
    this.teachingRepository = teachingRepository;
    this.attendanceRepository = attendanceRepository;
  }

  /* Study Date*/
  @GetMapping("/timetable/list")
  public String showTimetableList(Model model) {
    Iterable<Timetable> timetables = timetableRepository.findAll();
    model.addAttribute("timetables", timetables);
    return "manager/timetable-list";
  }

  @GetMapping("timetable/form")
  public String showTimetableForm(Model model) {
    model.addAttribute("timetable", new Timetable());
    model.addAttribute("allSemester", semesterRepository.findAll());
    model.addAttribute("allTeacher", teacherRepository.findAll());
    model.addAttribute("allSubject", subjectRepository.findAll());
    model.addAttribute("allClass", schoolClassRepository.findAll());
    model.addAttribute("allSchedule", scheduleRepository.findAll());
    return "manager/timetable-form";
  }

  @PostMapping("/timetable/save")
  public String saveTimetable(@ModelAttribute Timetable timetable) {
    timetableRepository.save(timetable);

    Date studyDate = timetable.getStudyDate();
    Semester semester = timetable.getSemester();
    Collection<Schedule> schedules = timetable.getSchedules();
    Collection<SchoolClass> schoolClasses = timetable.getSchoolClasses();
    Collection<Teacher> teachers = timetable.getTeachers();
    Collection<Subject> subjects = timetable.getSubjects();

    for (Schedule schedule : schedules) {
      for (SchoolClass schoolClass : schoolClasses) {
         for (Teacher teacher : teachers) {
          for (Subject subject : subjects) {
            Teaching existingTeaching = teachingRepository.findByScheduleAndSchoolClassAndTeacherAndSubject(
                schedule, schoolClass, teacher, subject);

            if (existingTeaching != null) {
              existingTeaching.setClassComments(""); // Khởi tạo một chuỗi trống

              teachingRepository.save(existingTeaching);
            } else {
              Teaching newTeaching = new Teaching();
              newTeaching.setTeachingDate(studyDate);
              newTeaching.setSchedule(schedule);
              newTeaching.setSchoolClass(schoolClass);
              newTeaching.setTeacher(teacher);
              newTeaching.setSubject(subject);
              newTeaching.setClassComments(""); // Khởi tạo một chuỗi trống

              teachingRepository.save(newTeaching);

              Collection<Student> students = schoolClass.getStudents();

              for (Student student : students) {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setAttendanceDate(studyDate);
                attendance.setTeacher(teacher);
                attendance.setSchedule(schedule);
                attendance.setPresent(false);

                attendanceRepository.save(attendance);
              }
            }
          }
        }
      }
    }

    return "redirect:/timetable/list";
  }


  @GetMapping("/timetable/edit/{id}")
  public String editTimetable(@PathVariable Long id, Model model) {
    Timetable timetable = timetableRepository.findById(id).orElse(null);
    model.addAttribute("timetable", timetable);
    model.addAttribute("allSemester", semesterRepository.findAll());
    model.addAttribute("allTeacher", teacherRepository.findAll());
    model.addAttribute("allSubject", subjectRepository.findAll());
    model.addAttribute("allClass", schoolClassRepository.findAll());
    model.addAttribute("allSchedule", scheduleRepository.findAll());
    return "manager/timetable-form";
  }

  @GetMapping("/timetable/delete/{id}")
  public String deleteTimetable(@PathVariable Long id) {
    Timetable timetable = timetableRepository.findById(id).orElse(null);

    if (timetable != null) {
      Collection<Schedule> schedules = timetable.getSchedules();
      Collection<SchoolClass> schoolClasses = timetable.getSchoolClasses();
      Collection<Teacher> teachers = timetable.getTeachers();
      Collection<Subject> subjects = timetable.getSubjects();

      for (Schedule schedule : schedules) {
        for (SchoolClass schoolClass : schoolClasses) {
          for (Teacher teacher : teachers) {
            for (Subject subject : subjects) {
              Teaching teachingToDelete = teachingRepository.findByScheduleAndSchoolClassAndTeacherAndSubject(
                  schedule, schoolClass, teacher, subject);
              if (teachingToDelete != null) {
                teachingRepository.delete(teachingToDelete);
              }

              Collection<Student> students = schoolClass.getStudents();
              for (Student student : students) {
                Attendance attendanceToDelete = attendanceRepository.findByScheduleAndStudentAndTeacher(
                    schedule, student, teacher);
                if (attendanceToDelete != null) {
                  attendanceRepository.delete(attendanceToDelete);
                }
              }
            }
          }
        }
      }
    }

    timetableRepository.deleteById(id);
    return "redirect:/timetable/list";
  }


  /*Schedule*/
  @GetMapping("/schedule/list")
  public String showScheduleList(Model model) {
    Iterable<Schedule> schedules = scheduleRepository.findAll();
    model.addAttribute("schedules", schedules);
    return "manager/schedule-list";
  }

  @GetMapping("schedule/form")
  public String showScheduleForm(Model model) {
    model.addAttribute("schedule", new Schedule());
    return "manager/schedule-form";
  }

  @PostMapping("/schedule/save")
  public String saveSchedule(@ModelAttribute Schedule schedule) {
    scheduleRepository.save(schedule);
    return "redirect:/schedule/list";
  }

  @GetMapping("/schedule/edit/{id}")
  public String editSchedule(@PathVariable Long id, Model model) {
    Schedule schedule = scheduleRepository.findById(id).orElse(null);
    model.addAttribute("schedule", schedule);
    return "manager/schedule-form";
  }

  @GetMapping("/schedule/delete/{id}")
  public String deleteSchedule(@PathVariable Long id) {
    scheduleRepository.deleteById(id);
    return "redirect:/schedule/list";
  }

}
