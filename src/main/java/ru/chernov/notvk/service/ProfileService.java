package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.mail.MailInfo;
import ru.chernov.notvk.mail.MailManager;
import ru.chernov.notvk.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Pavel Chernov
 */
@Service
public class ProfileService {

    private final UserService userService;
    private final MailManager mailManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ProfileService(UserService userService,
                          MailManager mailManager,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mailManager = mailManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateAvatar(User user, MultipartFile avatar) throws IOException {
        if (ImageUtils.isImageTypeAllowed(avatar)) {
            File uploadDir = new File(uploadPath);

            // если папка не существует
            if (!uploadDir.exists()) {
                // если не удалось создать папку
                if (!uploadDir.mkdir())
                    return;
            }

            String filename = UUID.randomUUID() + "." + avatar.getOriginalFilename();
            avatar.transferTo(new File(uploadPath + "/img/avatar/" + filename));

            user.setAvatarFilename(filename);
            userService.save(user);
        }
    }

    public void updateData(User user, String username, String name, String surname,
                           String status, LocalDate birthday) {
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setStatus(status);
        user.setBirthday(birthday);

        userService.save(user);
    }

    public boolean updateEmail(User user, String email) {
        if (!user.getEmail().equals(email)) {
            user.setEmail(email);
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            userService.save(user);
            mailManager.send(new MailInfo(user, "2"));
            return true;
        }
        return false;
    }

    public boolean updatePassword(User user, String oldPassword, String newPassword, String newPasswordConfirm) {
        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(newPasswordConfirm)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);
            mailManager.send(new MailInfo(user, "3"));
            return true;
        }
        return false;
    }
}
