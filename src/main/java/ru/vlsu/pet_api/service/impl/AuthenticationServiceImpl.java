package ru.vlsu.pet_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.dto.AuthenticationRequest;
import ru.vlsu.pet_api.dto.AuthenticationResponse;
import ru.vlsu.pet_api.dto.RegisterRequest;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.entity.Role;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl {

    private final PetUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        PetUser petUser = new PetUser();
        petUser.setEmail(request.getEmail());
        petUser.setPassword(passwordEncoder.encode(request.getPassword()));
        petUser.setRole(Role.USER);
        repository.save(petUser);
        String jwtToken = jwtService.generateToken(petUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(petUser.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

        PetUser petUser = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(petUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(petUser.getEmail())
                .build();
    }
}
