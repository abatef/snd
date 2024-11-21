package com.snd.snxdbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchResponseDTO {
    private Integer productId;
    private String productName;
    private Double price;
    private String description;
    private List<StoreDTO> stores; // List of stores offering this product

    @Data
    public static class StoreDTO {
        private Integer storeId;
        private String storeName;
        private Double distance; // calculated distance from the user
        private Double rating;
        private Integer stock;
    }
}
