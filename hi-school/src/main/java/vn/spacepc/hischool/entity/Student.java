package vn.spacepc.hischool.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.engine.internal.Cascade;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentId;

  @ManyToOne
      (cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_id")
  private Parent parent;

  @ManyToOne
  @JoinColumn(name = "school_id")
  private School school;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Transcript> transcripts ;

  @Lob
  @Column(name = "avatar")
  private Blob avatar;
  private String fullName;
  private Date dateOfBirth;
  private String nationality;
  private String ethnicity;
  private String citizenId;
  private Date issuedDate;
  private String issuedPlace;
  private String permanentAddress;
  private String contactAddress;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "email")
  private Email email;
  @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
  private Account account ;
  @ManyToMany
      (fetch = FetchType.LAZY,
          cascade = {
          CascadeType.PERSIST,
          CascadeType.DETACH,
          CascadeType.REFRESH,
          CascadeType.MERGE})
  @JoinTable(
      name = "class_student",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "class_id")
  )
  private Collection<SchoolClass> schoolClasses;

  @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
  private List<Score> scores;

  public Student() {
  }

  public Student(Long studentId, Parent parent, School school, List<Transcript> transcripts,
      Blob avatar, String fullName, Date dateOfBirth, String nationality, String ethnicity,
      String citizenId, Date issuedDate, String issuedPlace, String permanentAddress,
      String contactAddress, Email email, Account account, Collection<SchoolClass> schoolClasses,
      List<Score> scores) {
    this.studentId = studentId;
    this.parent = parent;
    this.school = school;
    this.transcripts = transcripts;
    this.avatar = avatar;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.nationality = nationality;
    this.ethnicity = ethnicity;
    this.citizenId = citizenId;
    this.issuedDate = issuedDate;
    this.issuedPlace = issuedPlace;
    this.permanentAddress = permanentAddress;
    this.contactAddress = contactAddress;
    this.email = email;
    this.account = account;
    this.schoolClasses = schoolClasses;
    this.scores = scores;
  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public Blob getAvatar() {
    return avatar;
  }

  public void setAvatar(Blob avatar) {
    this.avatar = avatar;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getEthnicity() {
    return ethnicity;
  }

  public void setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
  }

  public String getCitizenId() {
    return citizenId;
  }

  public void setCitizenId(String citizenId) {
    this.citizenId = citizenId;
  }

  public Date getIssuedDate() {
    return issuedDate;
  }

  public void setIssuedDate(Date issuedDate) {
    this.issuedDate = issuedDate;
  }

  public String getIssuedPlace() {
    return issuedPlace;
  }

  public void setIssuedPlace(String issuedPlace) {
    this.issuedPlace = issuedPlace;
  }

  public String getPermanentAddress() {
    return permanentAddress;
  }

  public void setPermanentAddress(String permanentAddress) {
    this.permanentAddress = permanentAddress;
  }


  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public List<Transcript> getTranscripts() {
    return transcripts;
  }

  public void setTranscripts(List<Transcript> transcripts) {
    this.transcripts = transcripts;
  }

  public String getContactAddress() {
    return contactAddress;
  }

  public void setContactAddress(String contactAddress) {
    this.contactAddress = contactAddress;
  }

  public void setAvatarFileName(String fileName) {
  }

  public Collection<SchoolClass> getSchoolClasses() {
    return schoolClasses;
  }

  public void setSchoolClasses(Collection<SchoolClass> schoolClasses) {
    this.schoolClasses = schoolClasses;
  }

  public List<Score> getScores() {
    return scores;
  }

  public void setScores(List<Score> scores) {
    this.scores = scores;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }



}
