package ru.chernov.notvk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pavel Chernov
 */
@Configuration
public class UploadConfig {

    @Bean
    public String uploadPath(@Value("${upload.path}") String uploadPath) {
        return uploadPath;
    }
}
