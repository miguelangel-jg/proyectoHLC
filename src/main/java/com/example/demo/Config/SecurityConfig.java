package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login", "/register", "/registrar", "/loginUsuario",
                                                                "/home", "/publicar",
                                                                "/css/**", "/js/**", "/images/**")
                                                .permitAll()) // Asegúrate de que todas las rutas de la aplicacion estan
                                                              // aqui
                                .formLogin(login -> login
                                                .loginPage("/login")) // Página de login personalizada
                                .csrf(csrf -> csrf.disable()); // Deshabilitar CSRF si es necesario

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // Usar BCrypt para encriptar las contraseñas
        }
}
