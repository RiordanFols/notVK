package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User changeData(@AuthenticationPrincipal User user,
                           @RequestBody Map<String, Object> body) {
        String username = body.get("username").toString();
        String email = body.get("email").toString();
        String name = body.get("name").toString();
        String surname = body.get("surname").toString();
        String status = body.get("status").toString();

        String password = null;
        if (body.containsKey("password")) {
            password = body.get("password").toString();
        }

        var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(body.get("birthday").toString(), dtf);

        return userService.changeData(user, email, username, name, surname, status, password, birthday);
    }
}
