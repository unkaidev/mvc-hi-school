package vn.spacepc.hischool.service;

import java.util.List;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.entity.Notification;

public interface NotificationService {

  Long countUnreadNotifications(String student);

  List<Notification> getNotificationsByUsername(String username);

  void saveNotification(Notification notification);
}
