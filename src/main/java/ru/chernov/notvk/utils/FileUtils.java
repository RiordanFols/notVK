package ru.chernov.notvk.utils;

import java.io.File;

/**
 * @author Pavel Chernov
 */
public class FileUtils {

    public static boolean isUploadFolderCreatedOrCreate(String uploadPath) {
        File uploadDir = new File(uploadPath);

        // если папка существует
        if (uploadDir.exists()) {
            return true;
        } else {
            // если не удалось создать папку, то возвращаем false
            return uploadDir.mkdir();
        }
    }

}
