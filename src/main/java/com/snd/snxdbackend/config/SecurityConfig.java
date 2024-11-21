package com.snd.snxdbackend.config;

import com.snd.snxdbackend.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public SecurityConfig(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/user/register").permitAll()
                .requestMatchers("/api/user/me").authenticated()
                .anyRequest().permitAll());
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer((auth) -> auth.jwt(
                (jwt) -> jwt.decoder(jwtDecoder())
                        .jwtAuthenticationConverter(new JwtToUserAuthenticationConverter(userRepository, redisTemplate))
        ));
        return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
    }
}
