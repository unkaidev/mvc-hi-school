package vn.spacepc.hischool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.User;

@Entity
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Account sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private Student receiver;
  @Column(columnDefinition = "TEXT")
  private String content;
  private LocalDateTime timestamp;
  @Enumerated(EnumType.STRING)
  private NotificationStatus status;


  public Notification() {
  }

  public Notification(Long id, Account sender, Student receiver, String content,
      LocalDateTime timestamp) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.content = content;
    this.timestamp = timestamp;
  }

  public Notification(Long id, Account sender, Student receiver, String content,
      LocalDateTime timestamp, NotificationStatus status) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.content = content;
    this.timestamp = timestamp;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Account getSender() {
    return sender;
  }

  public void setSender(Account sender) {
    this.sender = sender;
  }

  public Student getReceiver() {
    return receiver;
  }

  public void setReceiver(Student receiver) {
    this.receiver = receiver;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public NotificationStatus getStatus() {
    return status;
  }

  public void setStatus(NotificationStatus status) {
    this.status = status;
  }
}
