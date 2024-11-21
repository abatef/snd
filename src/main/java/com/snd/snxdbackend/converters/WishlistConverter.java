package com.snd.snxdbackend.converters;

import com.snd.snxdbackend.dtos.wishlist.WishlistDTO;
import com.snd.snxdbackend.models.wishlist.Wishlist;
import java.util.stream.Collectors;


public class WishlistConverter {

    public static WishlistDTO toWishlistDTO(Wishlist wishlist) {
        WishlistDTO dto = new WishlistDTO();
        dto.setWishlistId(wishlist.getId());
        dto.setUserId(wishlist.getUser().getId());
        dto.setItems(wishlist.getItems().stream()
                .map(WishlistItemConverter::toWishlistItemDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
