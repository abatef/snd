package com.snd.snxdbackend.converters;

import com.snd.snxdbackend.dtos.wishlist.WishlistItemDTO;
import com.snd.snxdbackend.models.wishlist.WishlistItem;
import org.springframework.stereotype.Component;

@Component
public class WishlistItemConverter {

    public static WishlistItemDTO toWishlistItemDTO(WishlistItem wishlistItem) {
        WishlistItemDTO dto = new WishlistItemDTO();
        dto.setProductId(wishlistItem.getProduct().getId());
        dto.setProductName(wishlistItem.getProduct().getName());
        dto.setPrice(wishlistItem.getProduct().getPrice().doubleValue());
        return dto;
    }
}
