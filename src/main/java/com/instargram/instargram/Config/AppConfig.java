package com.instargram.instargram.Config;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Configuration
public class AppConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static String getImageFileDirPath()
    {
        // 원하는 경로로 지정해주세요
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\img\\";
    }

    @Bean
    public static String getVideoFileDirPath()
    {
        // 원하는 경로로 지정해주세요
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\video\\";
    }
}
