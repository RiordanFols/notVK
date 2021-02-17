package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author Pavel Chernov
 */
@Service
public class FileService {

    private final String imagePath;
    private final String avatarPath;

    @Autowired
    public FileService(String imagePath, String avatarPath) {
        this.imagePath = imagePath;
        this.avatarPath = avatarPath;
    }

    public void deleteAvatar(String filename) throws IOException {
        File avatar = new File(avatarPath + filename);
        Files.deleteIfExists(avatar.toPath());
    }

    public String saveAvatar(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "." + file.getOriginalFilename();
        file.transferTo(new File(avatarPath + filename));
        return filename;
    }

    public void deleteImage (String filename) throws IOException {
        File image = new File(imagePath + filename);
        Files.deleteIfExists(image.toPath());
    }

    public String saveImage(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "." + file.getOriginalFilename();
        file.transferTo(new File(imagePath + filename));
        return filename;
    }

}
