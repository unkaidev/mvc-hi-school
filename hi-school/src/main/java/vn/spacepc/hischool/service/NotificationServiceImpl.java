package vn.spacepc.hischool.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.NotificationRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Notification;
import vn.spacepc.hischool.entity.NotificationStatus;
import vn.spacepc.hischool.entity.Student;

@Service

public class NotificationServiceImpl implements NotificationService {

  private NotificationRepository notificationRepository;
  private StudentService studentService;
  private TeacherService teacherService;
  private AccountService accountService;

  @Autowired

  public NotificationServiceImpl(NotificationRepository notificationRepository,
      StudentService studentService, TeacherService teacherService, AccountService accountService) {
    this.notificationRepository = notificationRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
    this.accountService = accountService;
  }


  public void sendNotification(Account sender, Student receiver, String content) {
    Notification notification = new Notification();
    notification.setSender(sender);
    notification.setReceiver(receiver);
    notification.setContent(content);
    notification.setTimestamp(LocalDateTime.now());
    notificationRepository.save(notification);
  }

  public List<Notification> getNotificationsByReceiver(Student receiver) {
    return notificationRepository.findByReceiver(receiver);
  }

  @Override
  public Long countUnreadNotifications(String username) {
    Student student = accountService.findStudentByUserName(username);
    NotificationStatus unreadStatus = NotificationStatus.UNREAD;

    Long unreadCount = notificationRepository.countUnreadNotificationsByStudent(student, unreadStatus);

    return unreadCount;
  }
  @Override
  public List<Notification> getNotificationsByUsername(String username) {
    Student student = accountService.findStudentByUserName(username);
    List<Notification> notifications = notificationRepository.findByReceiver(student);
    return notifications;
  }
  public void markNotificationAsRead(Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId).orElse(null);
    if (notification != null) {
      notification.setStatus(NotificationStatus.READ);
      notificationRepository.save(notification);
    }
  }

  @Override
  public void saveNotification(Notification notification) {
    notificationRepository.save(notification);
  }
}
