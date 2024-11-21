package com.snd.snxdbackend.dtos.store;

import com.snd.snxdbackend.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailabilityDTO {
    private Integer storeId;
    private String storeName;
    private Integer stock;
    private Double price;
    private double distance;
    private Address storeAddress;

    public ProductAvailabilityDTO(Integer id, String name, Integer stock, double v, Address address) {
        this.storeId = id;
        this.storeName = name;
        this.stock = stock;
        this.price = v;
        this.distance = 0;
        this.storeAddress = address;
    }
}
