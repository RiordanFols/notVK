package ru.chernov.notvk.domain;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
public enum Gender {
    MALE("stock_m.png"),
    FEMALE("stock_f.png"),
    UNDEFINED("stock_undefined.png");

    @Getter
    private final String stockAvatarFilename;

    Gender(String stockAvatarFilename) {
        this.stockAvatarFilename = stockAvatarFilename;
    }
}
