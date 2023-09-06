package vn.spacepc.hischool.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/password")
public class PasswordController {

  @GetMapping("/forgetPassword")
  public String forgetPasswordPage() {
    return "public/forget-password";
  }
}
