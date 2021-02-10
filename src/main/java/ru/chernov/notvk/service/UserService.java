package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Role;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.mail.MailInfo;
import ru.chernov.notvk.mail.MailManager;
import ru.chernov.notvk.repository.MessageRepository;
import ru.chernov.notvk.repository.UserRepository;

import java.util.*;

/**
 * @author Pavel Chernov
 */
@Service
public class UserService implements UserDetailsService {

    private static final String PASSWORDS_NOT_SAME = "Пароли не совпадают";
    private static final String USERNAME_IS_TAKEN = "Пользователь с таким именем уже существует";
    private static final String EMAIL_IS_TAKEN = "Эта почта уже занята другим пользователем";

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MailManager mailManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       MessageRepository messageRepository,
                       MailManager mailManager,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.mailManager = mailManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean checkUserExisting(long id) {
        return findById(id) != null;
    }

    public String checkRegistrationData(String username, String email, String password, String passwordConfirm) {
        // если пароль и подтверждение пароля не совпадают
        if (!password.equals(passwordConfirm))
            return PASSWORDS_NOT_SAME;

        // если username уже существует
        if (loadUserByUsername(username) != null) {
            return USERNAME_IS_TAKEN;
        }

        // если email уже привязан
        if (findByEmail(email) != null) {
            return EMAIL_IS_TAKEN;
        }

        return null;
    }

    public void registration(String username, String email, String name, String surname, String password) {
        User user = new User();
        user.setUsername(username);
        user.setAvatarFilename(user.M_AVATAR_STOCK_FILENAME);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));

        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        mailManager.send(new MailInfo(user, "1"));

        userRepository.save(user);
    }

    public void subscribe(long userId, long targetId) {
        User user = findById(userId);
        User target = findById(targetId);

        if (!user.getSubscriptions().contains(target)) {
            user.getSubscriptions().add(target);
            userRepository.save(user);
        }
    }

    public void unsubscribe(long userId, long targetId) {
        User user = findById(userId);
        User target = findById(targetId);

        if (user.getSubscriptions().contains(target)) {
            user.getSubscriptions().remove(target);
            userRepository.save(user);
        }
    }

    public Set<User> getAllContacts(long userId) {
        Set<Long> contactsIds = messageRepository.findContactsIds(userId);
        contactsIds.remove(userId);

        Set<User> contacts = new HashSet<>();
        for (long contactId : contactsIds) {
            contacts.add(findById(contactId));
        }

        return contacts;
    }

    public boolean activateUser(String code) {
        User user = findByActivationCode(code);

        if (user == null)
            return false;

        user.setActive(true);
        return true;
    }
}
