package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.AuthRequestDTO;
import com.upc.widegreenapi.dtos.AuthResponseDTO;
import com.upc.widegreenapi.dtos.RegisterRequestDTO;
import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.InvalidEmailException;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.security.CustomUserDetailsService;
import com.upc.widegreenapi.security.JwtUtil;
import com.upc.widegreenapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequestDTO request){
    Map<String, Object> response = new HashMap<>();
        try {
            UsuarioDTO newUserDTO = usuarioService.registrarUsuario(request);
            response.put("message", "Usuario creado exitosamente");
            response.put("id", newUserDTO.getIdUsuario());
            response.put("email", newUserDTO.getEmail());
            response.put("username", newUserDTO.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidEmailException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
