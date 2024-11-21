package com.snd.snxdbackend.dtos.wishlist;

import lombok.Data;
import java.util.List;

@Data
public class WishlistDTO {
    private Integer wishlistId;
    private Integer userId;
    private List<WishlistItemDTO> items;
}
