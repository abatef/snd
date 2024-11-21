package com.snd.snxdbackend.converters;

import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.dtos.store.StoreDTO;
import com.snd.snxdbackend.models.store.Store;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

public class StoreConverter {

    public static StoreDTO toStoreDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setAddress(store.getAddress());
        dto.setContactNumber(store.getContactNumber());
        dto.setEmail(store.getEmail());
        dto.setRating(store.getRating().doubleValue());
        dto.setTotalReviews(store.getTotalReviews());
        Location location = Location.of(store.getLocation());
        Point p = store.getLocation();
        System.out.println("Location: " + location.getLatitude() + " " + location.getLongitude());
        System.out.println("Coords: " + p.getX() + " " + p.getY());
        dto.setLocation(location);
        return dto;
    }

    public static Store toStore(StoreDTO dto) {
        Store store = new Store();
        if (dto.getId() != null && dto.getId() > 0) {
            store.setId(dto.getId());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            store.setName(dto.getName());
        }
        if (dto.getAddress() != null) {
            store.setAddress(dto.getAddress());
        }
        if (dto.getContactNumber() != null && !dto.getContactNumber().isEmpty()) {
            store.setContactNumber(dto.getContactNumber());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            store.setEmail(dto.getEmail());
        }
        if (dto.getRating() != null) {
            store.setRating(BigDecimal.valueOf(dto.getRating()));
        }
        if (dto.getTotalReviews() != null) {
            store.setTotalReviews(dto.getTotalReviews());
        }
        if (dto.getLocation() != null) {
            store.setLocation(dto.getLocation().toPoint());
        }
        return store;
    }
}

