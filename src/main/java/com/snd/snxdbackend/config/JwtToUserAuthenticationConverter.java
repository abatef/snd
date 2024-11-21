package com.snd.snxdbackend.config;


import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Duration;

public class JwtToUserAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public JwtToUserAuthenticationConverter(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String email = source.getClaimAsString("email");
        User user;
        if (Boolean.TRUE.equals(redisTemplate.hasKey("user:" + email))) {
            logger.info("email: {}", email);
            user = (User) redisTemplate.opsForValue().get(email);
        } else {
            user = userRepository.findByEmail(email).orElse(null);
        }
        if (user != null) {
            redisTemplate.opsForValue().set(email, user, Duration.ofMinutes(5));
            logger.info("email: {}", email);
            return new UsernamePasswordAuthenticationToken(user, source, user.getAuthorities());
        }
        return null;
    }
}
