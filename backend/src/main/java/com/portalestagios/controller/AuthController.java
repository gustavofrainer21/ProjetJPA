package com.portalestagios.controller;

import com.portalestagios.dto.JwtResponse;
import com.portalestagios.dto.LoginRequest;
import com.portalestagios.security.JwtUtil;
import com.portalestagios.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getAuthorities().stream()
                                                   .findFirst()
                                                   .map(auth -> {
                                                       String role = auth.getAuthority();
                                                       if (role.equals("ROLE_ESTUDANTE")) return Usuario.Perfil.ESTUDANTE;
                                                       if (role.equals("ROLE_EMPRESA")) return Usuario.Perfil.EMPRESA;
                                                       if (role.equals("ROLE_ADMINISTRADOR")) return Usuario.Perfil.ADMINISTRADOR;
                                                       return null;
                                                   })
                                                   .orElse(null)));
    }
}
