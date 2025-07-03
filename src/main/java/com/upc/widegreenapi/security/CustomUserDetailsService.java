package com.upc.widegreenapi.security;

import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Aquí solo los usuarios admin deben tener acceso
        String role = usuario.getRole() != null ? usuario.getRole() : "USER";

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .build();
    }
}
