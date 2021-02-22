package ru.chernov.notvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.notvk.domain.Gender;
import ru.chernov.notvk.service.UserService;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("login")
    public String loginPage() {

        return "guest/login";
    }

    @GetMapping("registration")
    public String registrationPage(Model model) {

        Map<Object, Object> data = new HashMap<>();
        data.put("genders", Gender.getAll());

        model.addAttribute("frontendData", data);
        return "guest/registration";
    }

    @PostMapping("registration")
    public String registration(@RequestParam String username,
                               @RequestParam String gender,
                               @RequestParam String email,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String password,
                               @RequestParam String passwordConfirm,
                               Model model) {
        String error = userService.checkRegistrationData(username, email, password, passwordConfirm);
        if (error == null) {
            userService.registration(username, gender, email, name, surname, password);
            return "redirect:/login";
        } else {
            model.addAttribute("error", error);
            return "guest/registration";
        }
    }

    @GetMapping("activation{code}")
    public String activation(@PathVariable String code) {
        if (userService.activateUser(code)) {
            return "redirect:/login";
        } else {
            return "error/404";
        }
    }
}
