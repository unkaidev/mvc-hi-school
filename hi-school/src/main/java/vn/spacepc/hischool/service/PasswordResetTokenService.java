package vn.spacepc.hischool.service;

import java.sql.Date;

public interface PasswordResetTokenService {

  void deleteTokenIsExpired();
}
