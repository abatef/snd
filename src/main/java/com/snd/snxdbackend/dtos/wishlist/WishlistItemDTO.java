package com.snd.snxdbackend.dtos.wishlist;

import lombok.Data;

@Data
public class WishlistItemDTO {
    private Integer productId;
    private String productName;
    private Double price;
}
