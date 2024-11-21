package com.snd.snxdbackend.controllers;

import com.snd.snxdbackend.converters.UserConverter;
import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.dtos.Notification;
import com.snd.snxdbackend.dtos.user.UserDTO;
import com.snd.snxdbackend.dtos.user.UserRegistrationDTO;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.services.NotificationService;
import com.snd.snxdbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final NotificationService notificationService;

    public UserController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO dto) {
        UserDTO userDTO = userService.registerUser(dto);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal User user, @RequestBody UserDTO dto) {
        UserDTO userDTO = userService.updateUserProfile(dto.getId(), dto);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserConverter.toUserDTO(user));
    }

    @PutMapping("/location/preferred")
    public ResponseEntity<UserDTO> updateUserLocation(@AuthenticationPrincipal User user, @RequestBody Location location) {
        UserDTO userDTO = userService.setPreferredLocation(user.getId(), location);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/location/live")
    public void updateLiveLocation(@AuthenticationPrincipal User user, @RequestBody Location location) {
        userService.setCurrentLocation(user.getId(), location);
    }

    @PostMapping("/deactivate")
    public ResponseEntity<UserDTO> deactivateUser(@AuthenticationPrincipal User user) {
        userService.deactivateUser(user.getId());
        return ResponseEntity.ok(userService.getUserDTOById(user.getId()));
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(@AuthenticationPrincipal User user) {
        List<Notification> notifications = notificationService.getUserNotifications(user.getId());
        return ResponseEntity.ok(notifications);
    }
}
