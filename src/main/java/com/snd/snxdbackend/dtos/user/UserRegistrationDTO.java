package com.snd.snxdbackend.dtos.user;

import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.Address;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserRole role;
    private Address address;
}
