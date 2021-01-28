package ru.chernov.notvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.notvk.service.UserService;

/**
 * @author Pavel Chernov
 */
@Controller
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("registration")
    public String registration(@RequestParam String username,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String password,
                               @RequestParam String passwordConfirm,
                               Model model) {
        String error = userService.checkRegistrationData(username, password, passwordConfirm);
        if (error == null) {
            userService.registration(username, name, surname, password);
            return "redirect:/login";
        } else {
            model.addAttribute("error", error);
            return "registration";
        }
    }
}
