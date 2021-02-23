package ru.chernov.notvk.page;

/**
 * @author Pavel Chernov
 */
public enum Notification {
    REGISTRATION_SUCCESSFUL(0, "Регистрация прошла успешно. Введите свои логин и пароль");

    private final int code;
    private final String description;

    Notification(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
