package ru.vlsu.pet_api.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vlsu.pet_api.dto.AuthenticationRequest;
import ru.vlsu.pet_api.dto.AuthenticationResponse;
import ru.vlsu.pet_api.dto.RegisterRequest;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.entity.Role;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetUserRepository;
import ru.vlsu.pet_api.service.impl.AuthenticationServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private PetUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        PetUser petUser = new PetUser();
        petUser.setEmail("test@example.com");
        petUser.setPassword("encodedPassword");
        petUser.setRole(Role.USER);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(repository.save(any(PetUser.class))).thenReturn(petUser);
        when(jwtService.generateToken(any(PetUser.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        verify(repository).save(any(PetUser.class));
        verify(jwtService).generateToken(any(PetUser.class));
    }

    @Test
    void testAuthenticateSuccess() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        PetUser petUser = new PetUser();
        petUser.setEmail("test@example.com");
        petUser.setPassword("encodedPassword");

        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(petUser));
        when(jwtService.generateToken(petUser)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(petUser);
    }

    @Test
    void testAuthenticateFailure() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongPassword");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.authenticate(request);
        });
        assertEquals("Authentication failed: Bad credentials", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}