package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Notification;
import vn.spacepc.hischool.entity.NotificationStatus;
import vn.spacepc.hischool.entity.Student;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

  List<Notification> findByReceiver(Student receiver);
  @Query("SELECT COUNT(n) FROM Notification n WHERE n.receiver = :student AND n.status = :status")
  Long countUnreadNotificationsByStudent(@Param("student") Student student, @Param("status") NotificationStatus status);
}
