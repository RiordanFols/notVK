package ru.chernov.notvk.page;

/**
 * @author Pavel Chernov
 */
public enum Error {
    PASSWORDS_NOT_SAME(0, "Пароли не совпадают"),
    USERNAME_IS_TAKEN(1, "Пользователь с таким именем уже существует"),
    EMAIL_IS_TAKEN(2, "Эта почта уже занята другим пользователем"),
    PASSWORD_IS_TOO_SHORT(3, "Пароль должен содержать минимум 6 символов");

    private final int code;
    private final String description;

    Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
