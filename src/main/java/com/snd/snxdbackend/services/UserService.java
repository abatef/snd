package com.snd.snxdbackend.services;

import com.snd.snxdbackend.converters.UserConverter;
import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.dtos.user.UserDTO;
import com.snd.snxdbackend.dtos.user.UserRegistrationDTO;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.repositories.UserRepository;
import com.snd.snxdbackend.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final WishlistService wishlistService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserService(UserRepository userRepository, WishlistService wishlistService, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.wishlistService = wishlistService;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public UserDTO registerUser(UserRegistrationDTO dto) {
        User user = UserConverter.toUser(dto);
        user.setIsActive(true);
        if (user.getRole() == UserRole.ADMIN) {
            user.setIsActive(false);
        }
        user = userRepository.save(user);
        user.setWishlist(wishlistService.createWishlistForUser(user));
        return UserConverter.toUserDTO(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    }

    public UserDTO getUserDTOById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return UserConverter.toUserDTO(user);
    }

    @Transactional
    public UserDTO updateUserProfile(Integer userId, UserDTO updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (updatedUser.getFirstName() != null && updatedUser.getLastName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getMiddleName() != null && updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getAddress() != null) {
            user.setAddress(updatedUser.getAddress());
        }
        return UserConverter.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO setPreferredLocation(Integer userId, Location location) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPreferredLocation(location.toPoint());
        user = userRepository.save(user);
        return UserConverter.toUserDTO(user);
    }

    // TODO: Use opsForGeo
    public void setCurrentLocation(Integer userId, Location location) {
        redisTemplate.opsForValue().set("user:" + userId + ":live:location", location);
    }

    // TODO: Use opsForGeo
    public Location getCurrentLocation(Integer userId) {
        return (Location) redisTemplate.opsForValue().get("user:" + userId + ":live:location");
    }

    @Async
    public void generateOTP(Integer userId) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int v = random.nextInt(10);
            otp.append(v);
        }

        redisTemplate.opsForValue().set("user:" + userId + ":otp", otp.toString());
    }


    private Boolean updatePassword(Integer userId, String password, String otp) {
        String userOtp = (String) redisTemplate.opsForValue().get("user:" + userId + ":otp");
        assert userOtp != null;
        if (userOtp.equals(otp)) {
            userRepository.findById(userId).ifPresent(user -> {user.setPasswordHash(password); userRepository.save(user);});
            return true;
        }
        return false;
    }

    @Transactional
    public void deactivateUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(false);
        userRepository.save(user);
    }
}
