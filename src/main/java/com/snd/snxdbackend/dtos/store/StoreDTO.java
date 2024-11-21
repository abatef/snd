package com.snd.snxdbackend.dtos.store;

import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Integer id;
    private String name;
    private Address address;
    private String contactNumber;
    private String email;
    private Double rating;
    private Integer totalReviews;
    private Location location;
}

