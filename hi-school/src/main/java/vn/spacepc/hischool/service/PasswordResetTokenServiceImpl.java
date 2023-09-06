package vn.spacepc.hischool.service;

import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.PasswordResetTokenRepository;
import vn.spacepc.hischool.entity.PasswordResetToken;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired

  public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }


  @Override
  @Transactional
  public void deleteTokenIsExpired() {
    List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findAll();
    for (PasswordResetToken passwordResetToken : passwordResetTokens) {
      if (passwordResetToken.isExpired()) {
        passwordResetToken.setAccount(null);
        passwordResetTokenRepository.delete(passwordResetToken);
      }
    }

  }
}
