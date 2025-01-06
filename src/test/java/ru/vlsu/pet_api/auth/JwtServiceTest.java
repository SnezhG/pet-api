package ru.vlsu.pet_api.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vlsu.pet_api.config.JwtConfig;
import ru.vlsu.pet_api.jwt.JwtService;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtConfig jwtConfig;

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;

    private String JWT_SECRET_MOCK = "ba1a447dbf5af5bf6c82c3238d47127e910ce9fc130def17ecf12bdb29d1620b76947fe2e39db215200260ace772f915f01c1b4bd7f21cc46daa4b4523cc7f8786a3956bc3edcd8cce9955232170e0932e2cf7c2f64eba3b4b6b8a1c0f3f151133cbef89f336551acb60906d42b37e3bfe61e227dd861650151311da8058f78b1838d59fa5790328be36fe9b6b666fd1b5e02dd5dfebe803faa3011c34fce414e9d103060bb5a6934a186c6153df351962ef561b84c0937a205c6b0204a17e675802d5cb6743efc7693a90b351af797fbf0df25e3d66b675203c2b9bd8c5692723b16b58e2cc13bd8f3e2e55db4a390b1bc61a16444a43095695c641decc24f4";

    @BeforeEach
    void setUp() {
        // Мокируем JwtConfig
        when(jwtConfig.getJwtSecret()).thenReturn(JWT_SECRET_MOCK);

        // Создаем пользователя для тестирования
        userDetails = User.builder()
                .username("testUser")
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    void generateToken_shouldGenerateValidToken() {
        // Генерация токена
        String token = jwtService.generateToken(userDetails);

        // Проверка, что токен не пустой
        assertNotNull(token);

        // Извлечение имени пользователя из токена и проверка
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testUser", extractedUsername);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        // Генерация токена
        String token = jwtService.generateToken(userDetails);

        // Проверка на валидность токена
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalseForInvalidToken() {
        // Генерация токена
        String token = jwtService.generateToken(userDetails);

        // Создаем другого пользователя для проверки
        UserDetails otherUser = User.builder()
                .username("otherUser")
                .password("password")
                .roles("USER")
                .build();

        // Проверка на валидность токена с другим пользователем
        boolean isValid = jwtService.isTokenValid(token, otherUser);
        assertFalse(isValid);
    }

    @Test
    void extractExpiration_shouldReturnCorrectExpirationDate() {
        // Генерация токена
        String token = jwtService.generateToken(userDetails);

        // Извлечение срока годности токена
        Date expiration = jwtService.extractExpiration(token);

        // Проверка, что дата истечения больше текущей даты
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void extractClaim_shouldReturnCorrectClaim() {
        // Генерация токена с дополнительным требуемым утверждением
        String token = jwtService.generateToken(Map.of("claimKey", "claimValue"), userDetails);

        // Извлечение утверждения из токена
        String claimValue = jwtService.extractClaim(token, claims -> claims.get("claimKey", String.class));

        // Проверка, что значение утверждения правильное
        assertEquals("claimValue", claimValue);
    }
}