package vn.spacepc.hischool.service;

import java.sql.Date;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Student;

public interface AccountService extends UserDetailsService {
  public List<Account> getAllAccounts();
  public Account getAccountById(long id);
  public Account addAccount(Account account);
  public Account updateAccount(Account account);
  public void deleteAccountById(long id);
  public Account findByUserName(String username);

  Account findUserByEmail(Email userEmail);

  void createPasswordResetTokenForUser(Account account, String token, Date expiryDate);

  Email findEmailByAccount(Account account);

  void changeUserPassword(Account account, String newPassword);

  Student findStudentByUserName(String username);
}
