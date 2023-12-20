package com.instargram.instargram.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    static String getImageFileDirPath()
    {
        // 원하는 경로로 지정해주세요
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\img\\";
    }

    @Bean
    static String getVideoFileDirPath()
    {
        // 원하는 경로로 지정해주세요
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\video\\";
    }
}
