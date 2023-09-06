package vn.spacepc.hischool.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import vn.spacepc.hischool.handle.GenericResponse;
import vn.spacepc.hischool.service.AccountService;
import vn.spacepc.hischool.service.EmailService;
import vn.spacepc.hischool.service.PasswordResetTokenService;

@Controller
@RequestMapping("/account")
public class AccountController {

  private AccountRepository accountRepository;
  private AccountService accountService;
  private RoleRepository roleRepository;
  private StudentRepository studentRepository;
  private TeacherRepository teacherRepository;
  private EmailService emailService;
  private MessageSource messageSource;
  private PasswordResetTokenRepository passwordResetTokenRepository;
  private PasswordResetTokenService passwordResetTokenService;

  @Autowired

  public AccountController(AccountRepository accountRepository, AccountService accountService,
      RoleRepository roleRepository, StudentRepository studentRepository,
      TeacherRepository teacherRepository, EmailService emailService, MessageSource messageSource,
      PasswordResetTokenRepository passwordResetTokenRepository,
      PasswordResetTokenService passwordResetTokenService) {
    this.accountRepository = accountRepository;
    this.accountService = accountService;
    this.roleRepository = roleRepository;
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.emailService = emailService;
    this.messageSource = messageSource;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
    this.passwordResetTokenService = passwordResetTokenService;
  }

  //Create default Admin account (first run)
/*    @PostConstruct
  public void insertAccount(){
    Account account1 = new Account();
    account1.setUserName("admin");
    account1.setPassword("$2a$12$uGPY6ULihEBdkfdE4UqcIO6rFzFlr.j9VfnAJR1zaysauSsNu2nNK");
    account1.setActive(true);

    Role role1 = new Role();
    role1.setName("ROLE_ADMIN");

    Collection<Role> roles = new ArrayList<>();
    roles.add(role1);

    account1.setRoles(roles);
    accountRepository.save(account1);
  }*/

  @GetMapping("/account-form")
  public String showAccountForm(Model model) {
    model.addAttribute("account", new Account());
    model.addAttribute("allRoles", roleRepository.findAll());
    model.addAttribute("allStudent", studentRepository.findAll());
    model.addAttribute("allTeacher", teacherRepository.findAll());
    return "admin/account-form";
  }

  @GetMapping("/account-list")
  public String showAccountList(Model model) {
    model.addAttribute("accounts", accountRepository.findAll());
    return "admin/account-list";
  }

  @GetMapping("/edit-password")
  public String showEditPassword(Model model, Principal principal) {
    Account account = accountService.findByUserName(principal.getName());
    model.addAttribute("account", account);

    model.addAttribute("allRoles", roleRepository.findAll());
    model.addAttribute("allStudent", studentRepository.findAll());
    model.addAttribute("allTeacher", teacherRepository.findAll());

    return "public/change-password";
  }

  @PostMapping("/change-password")
  public String saveAccount(
      Principal principal, Account account,
      @RequestParam(name = "currentPassword") String currentPassword,
      @RequestParam(name = "newPassword") String newPassword,
      @RequestParam(name = "confirmNewPassword") String confirmNewPassword,
      Model model) {

    // Validate the current password
    if (!BCrypt.checkpw(currentPassword, account.getPassword())) {
      model.addAttribute("error", "Current password is incorrect");
      return "public/change-password";
    }

    // Validate that new password and confirm new password match
    if (!confirmNewPassword.equals(newPassword)) {
      model.addAttribute("error", "New password and confirmation do not match");
      return "public/change-password";
    }

    // Update the password
    if (!newPassword.isEmpty() && !newPassword.isBlank()) {
      String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
      account.setPassword(hashedPassword);
      account.setStudent(account.getStudent());
      account.setTeacher(account.getTeacher());
      account.setRoles(account.getRoles());
      accountRepository.save(account);
    }
    // Set a success message in the Model
    model.addAttribute("successMessage",
        "Password has been reset successfully!");
    return "public/change-password";

  }


  @GetMapping("/search-list")
  public String accountList(@RequestParam(name = "search", required = false) String search,
      Model model) {
    if (search != null && !search.isEmpty()) {
      Account account = accountRepository.findByUserName(search);
      model.addAttribute("accounts", account);
    } else {
      return "redirect:/account/account-list";
    }
    return "admin/account-list";
  }

  @PostMapping("/saveAccount")
  public String saveAccount(@ModelAttribute Account account) {
    if (!account.getPassword().isEmpty()) {
      String newPassword = account.getPassword();
      String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
      account.setPassword(hashedPassword);
    }
    accountRepository.save(account);
    return "redirect:/account/account-list";
  }

  @GetMapping("/addAccountsForStudents")
  public String addAccountsForStudents() {
    List<Student> allStudents = studentRepository.findAll();

    for (Student student : allStudents) {
      boolean accountExists = accountRepository.existsByStudent_StudentId(student.getStudentId());

      if (!accountExists) {
        Account account = new Account();
        account.setStudent(student);
        account.setUserName("s" + student.getStudentId().toString());

        List<Role> studentRoles = new ArrayList<>();
        studentRoles.add(roleRepository.findByName("ROLE_STUDENT"));
        account.setRoles(studentRoles);
        account.setActive(true);

        String defaultPassword = "s" + student.getStudentId().toString();
        String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());
        account.setPassword(hashedPassword);

        accountRepository.save(account);
      }
    }
    return "redirect:/account/account-list";
  }

  @GetMapping("/addAccountsForTeachers")
  public String addAccountsForTeachers() {
    List<Teacher> allTeachers = teacherRepository.findAll();

    for (Teacher teacher : allTeachers) {
      boolean accountExists = accountRepository.existsByTeacher_TeacherId(teacher.getTeacherId());

      if (!accountExists) {
        Account account = new Account();
        account.setTeacher(teacher);
        account.setUserName("t" + teacher.getTeacherId().toString());

        List<Role> teacherRoles = new ArrayList<>();
        teacherRoles.add(roleRepository.findByName("ROLE_TEACHER"));
        account.setRoles(teacherRoles);
        account.setActive(true);

        String defaultPassword = "t" + teacher.getTeacherId().toString();
        String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());
        account.setPassword(hashedPassword);

        accountRepository.save(account);
      }
    }
    return "redirect:/account/account-list";
  }


  @GetMapping("/edit-account/{id}")
  public String editAccount(@PathVariable Long id, Model model) {
    Account account = accountRepository.findById(id).orElse(null);
    model.addAttribute("account", account);
    model.addAttribute("allRoles", roleRepository.findAll());
    model.addAttribute("allStudent", studentRepository.findAll());
    model.addAttribute("allTeacher", teacherRepository.findAll());
    return "admin/account-form";
  }

  @GetMapping("/delete-account/{id}")
  public String deleteAccount(@PathVariable Long id) {
    accountRepository.deleteById(id);
    return "redirect:/account/account-list";
  }

  private String getAppUrl(HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
        + request.getContextPath();
  }

  @PostMapping("/resetPassword")
  public String resetPassword(HttpServletRequest request,
      @RequestParam("email") Email userEmail, Model model) throws AccountNotFoundException {

    // Delete expired tokens
    passwordResetTokenService.deleteTokenIsExpired();

    Account account = accountService.findUserByEmail(userEmail);
    if (account == null) {
      throw new AccountNotFoundException();
    }

    // Create Random Token
    String token = UUID.randomUUID().toString();

    // Set ExpiryDate
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, 1);
    java.sql.Date expiryDate = new java.sql.Date(calendar.getTimeInMillis());

    try {
      accountService.createPasswordResetTokenForUser(account, token, expiryDate);

      SimpleMailMessage resetTokenEmail = constructResetTokenEmail(getAppUrl(request), token,
          account);

      emailService.sendEmail(userEmail.toString(), "Reset Password", resetTokenEmail.toString());
      model.addAttribute("successMessage",
          " A password reset code has been sent to your email. Please check and follow the instructions.");

      return "public/forget-password";
    } catch (Exception e) {
      model.addAttribute("successMessage",
          " A password reset code has been sent to your email. Please check and follow the instructions.");

      return "public/forget-password";
    }
  }


  private SimpleMailMessage constructResetTokenEmail(
      String contextPath, String token, Account account) {
    String url = contextPath + "/account/change-password-reset?token=" + token;
    String message = messageSource.getMessage("message.resetPassword", null, Locale.getDefault());
    return constructEmail("Reset Password", message + " \r\n" + url, account);
  }

  private SimpleMailMessage constructEmail(String subject, String body,
      Account account) {
    Email emailByAccount = accountService.findEmailByAccount(account);
    SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject(subject);
    email.setText(body);
    email.setTo(emailByAccount.toString());
    email.setFrom("suport.hischool@gmail.com");
    return email;
  }

  @GetMapping("/change-password-reset")
  public String showChangePasswordForm(@RequestParam("token") String token, Model model) {
    // Check if the token is valid.
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

    if (passwordResetToken == null || passwordResetToken.isExpired()) {

      model.addAttribute("failedMessage",
          "The token is invalid or has expired!");
      return "error/error-token";
    }

    // The token is valid, allowing the user to reset their password.
    model.addAttribute("token", token);

    return "/public/change-password-reset";
  }

  @PostMapping("/change-password-reset")
  public String processChangePassword(@RequestParam("token") String token,
      @RequestParam("password") String newPassword, Model model) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

    if (passwordResetToken == null || passwordResetToken.isExpired()) {
      model.addAttribute("failedMessage",
          "The token is invalid or has expired!");
      return "error/error-token";
    }

    if (!newPassword.isEmpty()) {
      String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
      accountService.changeUserPassword(passwordResetToken.getAccount(), hashedPassword);
    }

    model.addAttribute("successMessage",
        "The password has been successfully reset!");
    return "public/change-password-reset";
  }

}
