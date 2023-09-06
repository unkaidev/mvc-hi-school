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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @OneToOne
  @JoinColumn(name = "student_id")
  private Student student;
  @OneToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;
  @Column(name = "username")
  private String userName;
  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
  private PasswordResetToken passwordResetToken;
  private String password;
  private boolean active;
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.DETACH,
      CascadeType.REFRESH,
      CascadeType.MERGE})
  @JoinTable(
      name = "accounts_roles",
      joinColumns = @JoinColumn(name = "account_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Collection<Role> roles;

  public Account() {
  }

  public Account(String userName, String password, boolean active) {
    this.userName = userName;
    this.password = password;
    this.active = active;
  }

  public Account(Student student, Teacher teacher, String userName, String password, boolean active,
      Collection<Role> roles) {
    this.student = student;
    this.teacher = teacher;
    this.userName = userName;
    this.password = password;
    this.active = active;
    this.roles = roles;
  }

  public Account(long id, Student student, Teacher teacher, String userName,
      PasswordResetToken passwordResetToken, String password, boolean active,
      Collection<Role> roles) {
    this.id = id;
    this.student = student;
    this.teacher = teacher;
    this.userName = userName;
    this.passwordResetToken = passwordResetToken;
    this.password = password;
    this.active = active;
    this.roles = roles;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  public PasswordResetToken getPasswordResetToken() {
    return passwordResetToken;
  }

  public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
    this.passwordResetToken = passwordResetToken;
  }

  public boolean isActive() {
    return active;


  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return id == account.id && active == account.active && Objects.equals(student,
        account.student) && Objects.equals(teacher, account.teacher)
        && Objects.equals(userName, account.userName) && Objects.equals(
        passwordResetToken, account.passwordResetToken) && Objects.equals(password,
        account.password) && Objects.equals(roles, account.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, student, teacher, userName, passwordResetToken, password, active,
        roles);
  }
}

