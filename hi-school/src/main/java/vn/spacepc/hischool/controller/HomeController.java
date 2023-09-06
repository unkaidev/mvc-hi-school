package vn.spacepc.hischool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
@GetMapping
  public String showPublicPage(Model model){
  return "home";
}
}
