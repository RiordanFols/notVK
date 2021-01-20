package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Role;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.repository.UserRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Service
public class UserService implements UserDetailsService {

    private static final String PASSWORDS_NOT_SAME = "Пароли не совпадают";
    private static final String USERNAME_IS_TAKEN = "Пользователь с таким именем уже существует";

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean checkUserExisting(long id) {
        return userRepository.findById(id).orElse(null) != null;
    }

    public String checkRegistrationData(String username, String password, String passwordConfirm) {
        // если пароль и подтверждение пароля не совпадают
        if (!password.equals(passwordConfirm))
            return PASSWORDS_NOT_SAME;

        // если username уже существует
        if (loadUserByUsername(username) != null) {
            return USERNAME_IS_TAKEN;
        }

        return null;
    }

    public void registration(String username, String name, String surname, String password) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        // todo: activation
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
