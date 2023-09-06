package vn.spacepc.hischool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class PasswordResetToken {

  private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private Account account;


  private Date expiryDate;

  public PasswordResetToken() {
  }

  public PasswordResetToken(Long id, String token, Account account, Date expiryDate) {
    this.id = id;
    this.token = token;
    this.account = account;
    this.expiryDate = expiryDate;
  }

  public PasswordResetToken(String token, Account account) {
    this.token = token;
    this.account = account;
  }

  public PasswordResetToken(String token, Account account, Date expiryDate) {
    this.token = token;
    this.account = account;
    this.expiryDate = expiryDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public boolean isExpired() {
    if (expiryDate == null) {
      return false;
    }

    LocalDate currentDate = LocalDate.now();
    LocalDate expiryLocalDate = expiryDate.toLocalDate();

    return currentDate.isAfter(expiryLocalDate);
  }

}
