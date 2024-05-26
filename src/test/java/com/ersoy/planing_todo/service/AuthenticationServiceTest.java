package com.ersoy.planing_todo.service;


import com.ersoy.planing_todo.config.security.JwtService;
import com.ersoy.planing_todo.domain.dto.request.AuthenticationRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.RegisterRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.AuthenticationResponseDTO;
import com.ersoy.planing_todo.domain.model.ToDo;
import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.repository.UserRepository;
import com.ersoy.planing_todo.service.AuthenticationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;


    private User user;


    @BeforeEach
    public void setup() {
        user = getUser();
    }

    @Test
    public void registerTest() {

        String mockEncodedPass = RandomStringUtils.randomAlphabetic(5);

        when(passwordEncoder.encode(anyString())).thenReturn(mockEncodedPass);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(mockEncodedPass);
        AuthenticationResponseDTO response = authenticationService.register(getRegisterRequestDTOInstance());

        assertNotNull(response);
        assertEquals(mockEncodedPass, response.getToken());
    }


    @Test
    public void AuthenticationTest() {
        String mockEncodedPass = RandomStringUtils.randomAlphabetic(5);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn(mockEncodedPass);

        AuthenticationResponseDTO response = authenticationService.authenticate(getAuthenticationRequestDTOInstance());

        assertNotNull(response);
        assertEquals(mockEncodedPass, response.getToken());
    }


    private RegisterRequestDTO getRegisterRequestDTOInstance() {
        return RegisterRequestDTO.builder()
                .fistName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    private User getUser() {
        return User.builder().id(RandomStringUtils.randomAlphabetic(5))
                .firstName(RandomStringUtils.randomAlphabetic(5))
                .lastName(RandomStringUtils.randomAlphabetic(5))
                .email("test@test.com")
                .password(RandomStringUtils.randomAlphabetic(5)).build();
    }

    private AuthenticationRequestDTO getAuthenticationRequestDTOInstance() {
        return AuthenticationRequestDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
