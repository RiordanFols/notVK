package ru.chernov.notvk.mail;

import lombok.Data;
import ru.chernov.notvk.domain.entity.User;

/**
 * @author Pavel Chernov
 */
@Data
public class MailInfo {
    private User user;

    // 1 - activation
    // 2 - email update
    // 3 - password update
    private String  code;

    public MailInfo(User user, String code) {
        this.user = user;
        this.code = code;
    }
}
