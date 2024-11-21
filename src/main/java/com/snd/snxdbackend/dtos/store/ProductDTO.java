package com.snd.snxdbackend.dtos.store;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String sku;
    private String barcode;
}

