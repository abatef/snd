package com.snd.snxdbackend.converters;

import com.snd.snxdbackend.dtos.user.UserDTO;
import com.snd.snxdbackend.dtos.user.UserRegistrationDTO;
import com.snd.snxdbackend.models.User;

public class UserConverter {

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
//        dto.setLastLogin(user.getLastLogin().toString());
        return dto;
    }

    public static User toUser(UserRegistrationDTO dto) {
        User user = new User();
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getMiddleName() != null && !dto.getMiddleName().isEmpty()) {
            user.setMiddleName(dto.getMiddleName());
        }
        if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(dto.getPassword());
        }
        user.setIsActive(true);
        return user;
    }
}
