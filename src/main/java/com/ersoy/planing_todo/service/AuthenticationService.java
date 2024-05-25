package com.ersoy.planing_todo.service;

import com.ersoy.planing_todo.config.security.JwtService;
import com.ersoy.planing_todo.domain.dto.request.AuthenticationRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.RegisterRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.AuthenticationResponseDTO;
import com.ersoy.planing_todo.domain.enums.Role;
import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponseDTO register(RegisterRequestDTO request) {

        var user = User.builder()
                .firstName(request.getFistName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.info("user created successfully  user: {}",user);
        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail()
                        , request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);

        log.info("Token created for this user: {}",user);
        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }
}
