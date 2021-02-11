package ru.chernov.notvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.ProfileService;
import ru.chernov.notvk.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping("/update/avatar")
    public String updateProfilePhoto(@AuthenticationPrincipal User user,
                                   @RequestParam("avatar") MultipartFile avatar) throws IOException {
        profileService.updateAvatar(user, avatar);

        return "redirect:/profile";
    }

    @PostMapping("/update/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String username,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String status,
                                @RequestParam(name = "birthday") String birthdayString,
                                @RequestParam String email) {

        var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthdayString, dtf);

        profileService.updateData(user, username, name, surname, status, birthday);

        if (profileService.updateEmail(user, email)) {
            return "redirect:/login";
        }

        return "redirect:/profile";
    }

    @PostMapping("/update/password")
    public String updatePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordConfirm) {
        if (profileService.updatePassword(user, oldPassword, newPassword, newPasswordConfirm)) {
            return "redirect:/login";
        } else {
            return "redirect:/profile";
        }
    }
}
