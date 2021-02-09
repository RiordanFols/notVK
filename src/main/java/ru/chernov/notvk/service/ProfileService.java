package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.mail.MailInfo;
import ru.chernov.notvk.mail.MailManager;
import ru.chernov.notvk.repository.UserRepository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Pavel Chernov
 */
@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final MailManager mailManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(UserRepository userRepository, MailManager mailManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailManager = mailManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void changeData(User user, String username, String name, String surname,
                           String status, LocalDate birthday) {
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setStatus(status);
        user.setBirthday(birthday);

        userRepository.save(user);
    }

    public boolean changeEmail(User user, String email) {
        if (!user.getEmail().equals(email)) {
            user.setEmail(email);
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            userRepository.save(user);
            mailManager.send(new MailInfo(user, "2"));
            return true;
        }
        return false;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword, String newPasswordConfirm) {
        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(newPasswordConfirm)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            mailManager.send(new MailInfo(user, "3"));
            return true;
        }
        return false;
    }
}
