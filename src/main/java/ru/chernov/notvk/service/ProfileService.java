package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.mail.MailInfo;
import ru.chernov.notvk.mail.MailManager;
import ru.chernov.notvk.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public User updateAvatar(User user, MultipartFile avatar) throws IOException {
        if (ImageUtils.isImageTypeAllowed(avatar)) {
            File uploadDir = new File(uploadPath);

            // если папка не существует
            if (!uploadDir.exists()) {
                // если не удалось создать папку
                if (!uploadDir.mkdir())
                    return user;
            }

            Files.deleteIfExists(new File(user.getAvatarFilename()).toPath());
            String filename = UUID.randomUUID() + "." + avatar.getOriginalFilename();
            avatar.transferTo(new File(uploadPath + "/img/avatar/" + filename));

            user.setAvatarFilename(filename);
        }

        return userService.save(user);
    }

    public User deleteAvatar(User user) throws IOException {

        // если у пользователя не стоковый аватар
        if (!user.getAvatarFilename().equals(user.getGender().getStockAvatarFilename())) {
            File avatar = new File(uploadPath + "/img/avatar" + user.getAvatarFilename());

            Files.deleteIfExists(avatar.toPath());
            user.setAvatarFilename(user.getGender().getStockAvatarFilename());
        }

        return userService.save(user);
    }

    public void updateData(User user, String username, String name, String surname,
                           String status, LocalDate birthday) {

        // если юзер с таким юзернэймом еще не существует или это текущий юзер
        if (userService.findByUsername(username) == null || userService.findByUsername(username).equals(user)) {
            user.setUsername(username);
            user.setName(name);
            user.setSurname(surname);
            user.setStatus(status);
            user.setBirthday(birthday);

            userService.save(user);
        }
        // else error
    }

    public boolean updateEmail(User user, String email) {

        // если юзер с такой почтой еще не существует или это текущий юзер
        if (userService.findByEmail(email) == null || userService.findByEmail(email).equals(user)) {
            if (!user.getEmail().equals(email)) {
                user.setEmail(email);
                user.setActive(false);
                user.setActivationCode(UUID.randomUUID().toString());
                userService.save(user);
                mailManager.send(new MailInfo(user, "2"));
                return true;
            }
        }
        // else error

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