package com.instargram.instargram.Member.Config.SpringSecurity;

import com.instargram.instargram.Member.Config.OAuth2.PrincipalOauth2UserService;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Builder
public class SecurityConfig {

    private static final String SOCIAL_LOGIN = "social_login";

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**"))
                        .permitAll())
//                        .requestMatchers("/board/**","/css/**", "/member/**", "/error/**", "/files/**").permitAll()
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/member/login").permitAll()
//                        .requestMatchers("/member/signup").permitAll()
//                        .requestMatchers("/member/signup/social").permitAll()
//                        .anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .oauth2Login((oauth2Login) -> oauth2Login
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/member/signup/social")
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig.userService(principalOauth2UserService))
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/member/login"));
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
