package com.api.app.controller;

import com.api.app.model.Principal;
import com.api.app.controller.security.Provider;
import com.api.app.model.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class AuthenticationController {
  private final Provider provider;

  @GetMapping("login")
  public String loginPage(Model model) {
    model.addAttribute("principal", new Principal());
    return "login";
  }

  @PostMapping("authenticate")
  public RedirectView authenticate(@ModelAttribute Principal principal) {
    try {
      provider.authenticate(principal);
      return new RedirectView("/");
    } catch (ForbiddenException e) {
      return new RedirectView("/login");
    }
  }

  @PostMapping("logout")
  public RedirectView logout() {
    provider.clearSession();
    return new RedirectView("/login");
  }
}
