package com.example.tacocloud.web;

import com.example.tacocloud.data.UserRepository;
import com.example.tacocloud.domain.User;
import com.example.tacocloud.security.RegistrationForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "newUser")
    private RegistrationForm register() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm(Model model) {

        model.addAttribute("newUser", register());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("newUser") RegistrationForm form) {
        User formUser = form.toUser(passwordEncoder);
        log.info("does this hash???? " + formUser);
        userRepo.save(formUser);
        return "redirect:/login";
    }
}