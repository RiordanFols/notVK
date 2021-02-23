package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.component.FileHandler;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.mail.MailInfo;
import ru.chernov.notvk.mail.MailManager;
import ru.chernov.notvk.utils.ImageUtils;

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
    private final FileHandler fileHandler;

    @Autowired
    public ProfileService(UserService userService, MailManager mailManager,
                          PasswordEncoder passwordEncoder, FileHandler fileHandler) {
        this.userService = userService;
        this.mailManager = mailManager;
        this.passwordEncoder = passwordEncoder;
        this.fileHandler = fileHandler;
    }

    public User updateAvatar(long userId, MultipartFile avatar) throws IOException {

        User user = userService.findById(userId);

        if (ImageUtils.isImageTypeAllowed(avatar)) {

            // если у пользователя не стоковый аватар
            if (!user.getAvatarFilename().equals(user.getGender().getStockAvatarFilename()))
                // удаляем его старое фото
                fileHandler.deleteAvatar(user.getAvatarFilename());

            String filename = fileHandler.saveAvatar(avatar);
            user.setAvatarFilename(filename);
        }

        return userService.save(user);
    }

    public User deleteAvatar(long userId) throws IOException {

        User user = userService.findById(userId);

        // если у пользователя не стоковый аватар
        if (!user.getAvatarFilename().equals(user.getGender().getStockAvatarFilename())) {
            // удаляем его аватар
            fileHandler.deleteAvatar(user.getAvatarFilename());
            // ставим стоковое фото
            user.setAvatarFilename(user.getGender().getStockAvatarFilename());
        }

        return userService.save(user);
    }

    public void updateData(long userId, String username, String gender, String name, String surname,
                           String status, LocalDate birthday) {

        User user = userService.findById(userId);
        // если юзернейм свободен или занят текущим юзером
        if (userService.findByUsername(username) == null || userService.findByUsername(username).equals(user)) {
            user.setUsername(username);
            user.setGender(gender);
            user.setName(name);
            user.setSurname(surname);
            user.setStatus(status);
            user.setBirthday(birthday);

            userService.save(user);
        }
        // else error
    }

    public boolean updateEmail(long userId, String email) {

        User user = userService.findById(userId);
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

    public boolean updatePassword(long userId, String oldPassword, String newPassword, String newPasswordConfirm) {
        User user = userService.findById(userId);

        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(newPasswordConfirm)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);
            mailManager.send(new MailInfo(user, "3"));
            return true;
        }
        return false;
    }
}
