package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.sql.Time;
import java.time.LocalTime;

@Entity
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long scheduleId;

  private String scheduleName;
  private LocalTime startTime;
  private LocalTime endTime;

  public Schedule() {
  }

  public Schedule(Long scheduleId, String scheduleName, LocalTime startTime, LocalTime endTime) {
    this.scheduleId = scheduleId;
    this.scheduleName = scheduleName;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public String getScheduleName() {
    return scheduleName;
  }

  public void setScheduleName(String scheduleName) {
    this.scheduleName = scheduleName;
  }

}

