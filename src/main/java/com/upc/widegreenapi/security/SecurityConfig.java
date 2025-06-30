package com.upc.widegreenapi.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
        //Security en Swagger
)
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/usuarios/listar").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/imagenes/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/perfil/registrar").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/publicaciones/**").authenticated()
                        .requestMatchers("/api/noticias/crear").hasRole("ADMIN")
                        .requestMatchers("/api/noticias/**").authenticated()
                        .requestMatchers("/api/eventos/registrar").hasRole("ADMIN")
                        .requestMatchers("/api/eventos/**").authenticated()
                        .requestMatchers("/api/organizadores/**").hasRole("ADMIN")
                        .requestMatchers("/api/publicaciones/crear").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/publicaciones/editar/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/publicaciones/eliminar/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/publicaciones").permitAll()
                        .requestMatchers("/api/comentarios/crear").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/comentarios/publicacion/**").permitAll()
                        .requestMatchers("/api/comentarios/editar/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/comentarios/eliminar/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/categorias/crear").hasRole("ADMIN")
                        .requestMatchers("/api/categorias/editar").hasRole("ADMIN")
                        .requestMatchers("/api/categorias/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/api/categorias/listar").permitAll()
                        .requestMatchers("/api/publicacion-categoria/asociar").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/publicacion-categoria/publicacion/**").permitAll()
                        .requestMatchers("/api/inscripciones/CantidadPorEvento").hasRole("ADMIN")
                        .requestMatchers("/api/eventos/ubicacion/**").authenticated()
                        .requestMatchers("/api/organizadores/eliminar/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
