package com.snd.snxdbackend.dtos.user;

import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.Address;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Address address;
    private UserRole role;
    private boolean isActive;
    private String lastLogin;
    private Location preferredLocation;
}
