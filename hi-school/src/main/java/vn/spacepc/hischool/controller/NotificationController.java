package vn.spacepc.hischool.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.spacepc.hischool.dao.NotificationRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Notification;
import vn.spacepc.hischool.entity.NotificationStatus;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.service.AccountService;
import vn.spacepc.hischool.service.NotificationService;
import vn.spacepc.hischool.service.SchoolClassService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TeacherService;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

  private NotificationService notificationService;
  private NotificationRepository notificationRepository;
  private StudentService studentService;
  private TeacherService teacherService;
  private AccountService accountService;
  private SchoolClassService schoolClassService;

  @Autowired

  public NotificationController(NotificationService notificationService,
      NotificationRepository notificationRepository, StudentService studentService,
      TeacherService teacherService, AccountService accountService,
      SchoolClassService schoolClassService) {
    this.notificationService = notificationService;
    this.notificationRepository = notificationRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
    this.accountService = accountService;
    this.schoolClassService = schoolClassService;
  }


  @GetMapping("/count")
  @ResponseBody
  public Long getUnreadNotificationCount(Principal principal) {
    String username = principal.getName();
    return notificationService.countUnreadNotifications(username);
  }

  @GetMapping("/list")
  public String listNotifications(Model model, Principal principal) {
    String username = principal.getName();
    List<Notification> notifications = notificationService.getNotificationsByUsername(username);

    model.addAttribute("notifications", notifications);
    return "student/notifications-list";
  }


  @GetMapping("/mark-as-read/{notificationId}")
  @ResponseBody
  public void markNotificationAsRead(@PathVariable Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId).orElse(null);
    if (notification != null && notification.getStatus() == NotificationStatus.UNREAD) {
      notification.setStatus(NotificationStatus.READ);
      notificationRepository.save(notification);
    }
  }

  @GetMapping(value = "/show-full-content/{notificationId}", produces = MediaType.TEXT_HTML_VALUE
      + ";charset=UTF-8")
  @ResponseBody
  public String showFullContent(@PathVariable Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId).orElse(null);
    if (notification != null) {
      return notification.getContent();
    }
    return "";
  }

  @GetMapping("/manager/send")
  public String showSendNotificationFormManager(Model model) {
    List<Student> students = studentService.getAllStudents();
    model.addAttribute("students", students);
    return "manager/send-notification-form";
  }

  @GetMapping("/head-teacher/send")
  public String showSendNotificationFormHeadTeacher(Model model, Principal principal) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (authentication != null && authentication.isAuthenticated()) {
      Account account = accountService.findByUserName(username);
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
        return "manager/send-notification-form";
      }
    }
    return "home";
  }

  @PostMapping("/send")
  public String sendNotification(@RequestParam("content") String content,
      @RequestParam(value = "receivers", required = false) List<Long> receiverIds,
      Principal principal, Model model) {
    String username = principal.getName();
    Account account = accountService.findByUserName(username);
    if (receiverIds != null && !receiverIds.isEmpty()) {
      for (Long receiverId : receiverIds) {
        Student receiver = studentService.getStudentById(
            receiverId);
        if (receiver != null) {
          Notification notification = new Notification();
          notification.setContent(content);
          notification.setReceiver(receiver);
          notification.setStatus(NotificationStatus.UNREAD);
          notification.setTimestamp(LocalDateTime.now());
          notification.setSender(account);

          notificationService.saveNotification(notification);
        }
      }
    }
    model.addAttribute("successMessage",
        "The message has been successfully send!");
    return "manager/send-notification-form";
  }

  @GetMapping("/delete/{id}")
  public String deleteNotification(@PathVariable Long id) {
    notificationRepository.deleteById(id);
    return "redirect:/notifications/list";
  }

}
