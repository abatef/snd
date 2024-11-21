package com.snd.snxdbackend.dtos;

import lombok.Data;

@Data
public class ProductSearchRequestDTO {
    private String productName;
    private String category;
    private Double maxPrice;
    private Double minPrice;
    private Double distance; // in kilometers
    private Double userLatitude;
    private Double userLongitude;
    private Location userLocation;
    private String sortBy; // price, distance, rating
}
