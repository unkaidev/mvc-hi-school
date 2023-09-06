package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.PasswordResetTokenRepository;
import vn.spacepc.hischool.dao.RoleRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.PasswordResetToken;
import vn.spacepc.hischool.entity.Role;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;

@Service
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;
  private RoleRepository roleRepository;
  private PasswordResetTokenRepository passwordResetTokenRepository;
  private StudentRepository studentRepository;
  private TeacherRepository teacherRepository;
  private StudentService studentService;
  private TeacherService teacherService;
  private PasswordResetTokenService passwordResetTokenService;

  @Autowired

  public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository,
      PasswordResetTokenRepository passwordResetTokenRepository, StudentRepository studentRepository,
      TeacherRepository teacherRepository, StudentService studentService,
      TeacherService teacherService, PasswordResetTokenService passwordResetTokenService) {
    this.accountRepository = accountRepository;
    this.roleRepository = roleRepository;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.studentService = studentService;
    this.teacherService = teacherService;
    this.passwordResetTokenService = passwordResetTokenService;
  }


  @Override
  public Account findByUserName(String username) {
    return accountRepository.findByUserName(username);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepository.findByUserName(username);
    if (username == null) {
      throw new UsernameNotFoundException("Invalid Username or password: ");
    }
    return new org.springframework.security.core.userdetails.User(
        account.getUserName(), account.getPassword(), rolesToAuthorities(account.getRoles()));
  }

  private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public Account getAccountById(long id) {
    return accountRepository.getById(id);
  }

  @Override
  @Transactional
  public Account addAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  @Transactional
  public Account updateAccount(Account account) {
    return accountRepository.saveAndFlush(account);
  }

  @Override
  @Transactional
  public void deleteAccountById(long id) {
    accountRepository.deleteById(id);
  }

  @Override
  public Account findUserByEmail(Email userEmail) {

    Student student = studentService.findByEmail(userEmail);
    if (student != null) {
      return student.getAccount();
    }

    Teacher teacher = teacherService.findByEmail(userEmail);
    if (teacher != null) {
      return teacher.getAccount();
    }
    return null;
  }


  @Override
  public void createPasswordResetTokenForUser(Account account, String token, Date expiryDate) {
    PasswordResetToken myToken = new PasswordResetToken(token, account, expiryDate);
    passwordResetTokenRepository.save(myToken);
  }

  @Override
  public Email findEmailByAccount(Account account) {
    Student student = account.getStudent();
    if (student != null) {
      return student.getEmail();
    }
    Teacher teacher = account.getTeacher();
    if (teacher != null) {
      return teacher.getEmail();
    }
    return null;
  }


  @Override
  public void changeUserPassword(Account account, String newPassword) {
    // Mã hóa mật khẩu mới trước khi lưu vào cơ sở dữ liệu
    account.setPassword(newPassword);

    // Lưu tài khoản đã được cập nhật vào cơ sở dữ liệu
    accountRepository.save(account);
  }

  @Override
  public Student findStudentByUserName(String username) {
    Account account = accountRepository.findByUserName(username);
    Student student = account.getStudent();
    return student;
  }
}
