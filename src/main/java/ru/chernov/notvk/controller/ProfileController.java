package ru.chernov.notvk.controller;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.ProfileService;

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

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/update/avatar")
    public String updateAvatar(@AuthenticationPrincipal User user,
                               @RequestParam("avatar") MultipartFile avatar) {

        try {
            profileService.updateAvatar(user, avatar);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
