package com.snd.snxdbackend.dtos.store;

import com.snd.snxdbackend.models.store.StoreProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreProductDTO {
    private Integer storeId;
    private String storeName;
    private Integer productId;
    private String productName;
    private Double price;
    private Integer stock;
    private String storeAddress;

    public StoreProductDTO(StoreProduct storeProduct) {
        this.storeId = storeProduct.getStore().getId();
        this.storeName = storeProduct.getStore().getName();
        this.productId = storeProduct.getProduct().getId();
        this.productName = storeProduct.getProduct().getName();
        this.price = storeProduct.getPrice();
        this.stock = storeProduct.getStock();
        this.storeAddress = storeProduct.getStore().getAddress().toString();
    }
}
