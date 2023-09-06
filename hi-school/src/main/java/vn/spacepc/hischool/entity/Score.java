package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.text.DecimalFormat;
import java.util.List;

@Entity
public class Score {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long scoreId;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;
  @ManyToOne
  @JoinColumn(name = "semester_id")
  private Semester semester;
  private Double dailyScore;
  private Double midtermScore;
  private Double finalScore;
  @ManyToOne
  @JoinColumn(name = "teacher_assignment_id")
  private TeacherAssignment teacherAssignment;
  private String subjectEvaluation;

  public Score() {
  }

  public Score(Long scoreId, Subject subject, Student student, Semester semester, Double dailyScore,
      Double midtermScore, Double finalScore, TeacherAssignment teacherAssignment,
      String subjectEvaluation) {
    this.scoreId = scoreId;
    this.subject = subject;
    this.student = student;
    this.semester = semester;
    this.dailyScore = dailyScore;
    this.midtermScore = midtermScore;
    this.finalScore = finalScore;
    this.teacherAssignment = teacherAssignment;
    this.subjectEvaluation = subjectEvaluation;
  }

  public Long getScoreId() {
    return scoreId;
  }

  public void setScoreId(Long scoreId) {
    this.scoreId = scoreId;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public String getSubjectEvaluation() {
    return subjectEvaluation;
  }

  public void setSubjectEvaluation(String subjectEvaluation) {
    this.subjectEvaluation = subjectEvaluation;
  }

  public Double getDailyScore() {
    return dailyScore;
  }

  public void setDailyScore(Double dailyScore) {
    this.dailyScore = dailyScore;
  }

  public Double getMidtermScore() {
    return midtermScore;
  }

  public void setMidtermScore(Double midtermScore) {
    this.midtermScore = midtermScore;
  }

  public Double getFinalScore() {
    return finalScore;
  }

  public void setFinalScore(Double finalScore) {
    this.finalScore = finalScore;
  }

  public TeacherAssignment getTeacherAssignment() {
    return teacherAssignment;
  }

  public void setTeacherAssignment(TeacherAssignment teacherAssignment) {
    this.teacherAssignment = teacherAssignment;
  }

  @Override
  public String toString() {
    return "Score{" +
        "scoreId=" + scoreId +
        ", subject=" + subject +
        ", student=" + student +
        ", semester=" + semester +
        ", dailyScore=" + dailyScore +
        ", midtermScore=" + midtermScore +
        ", finalScore=" + finalScore +
        '}';
  }

  public Double calculateTotalScore() {
    final double DAILY_SCORE_WEIGHT = 0.1;
    final double MIDTERM_SCORE_WEIGHT = 0.3;
    final double FINAL_SCORE_WEIGHT = 0.6;
    double totalScore =
        (dailyScore * DAILY_SCORE_WEIGHT) + (midtermScore * MIDTERM_SCORE_WEIGHT) + (finalScore
            * FINAL_SCORE_WEIGHT);
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    String roundedTotalScore = decimalFormat.format(totalScore);
    return Double.parseDouble(roundedTotalScore);
  }

  public Double calculateAverageScore(List<Score> scores) {
    if (scores == null || scores.isEmpty()) {
      return 0.0;
    }

    double sum = 0.0;
    for (Score score : scores) {
      sum += score.calculateTotalScore();
    }

    double average = sum / scores.size();
    return Math.round(average * 10.0) / 10.0;
  }
  public String getGrade() {
    double averageScore = this.calculateTotalScore();

    if (averageScore >= 8.5) {
      return "A";
    } else if (averageScore >= 7.8) {
      return "B+";
    } else if (averageScore >= 7.0) {
      return "B";
    } else if (averageScore >= 6.3) {
      return "C+";
    } else if (averageScore >= 5.5) {
      return "C";
    }else if (averageScore >= 4.8) {
      return "D+";
    }else if (averageScore >= 4.0) {
      return "D";
    }else {
      return "F";
    }
  }

}
