package org.example.jzksglxt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {



    /**
     * 认证管理器
     * @param authenticationConfiguration 认证配置
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置WebSecurity
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 允许所有请求访问
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                // 关闭CSRF保护（便于开发）
                .csrf(csrf -> csrf.disable())
                // 关闭表单登录
                .formLogin(formLogin -> formLogin.disable())
                // 关闭HTTP基本认证
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}