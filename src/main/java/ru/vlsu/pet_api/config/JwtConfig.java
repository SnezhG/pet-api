package ru.vlsu.pet_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }
}
