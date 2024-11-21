package com.snd.snxdbackend.controllers;

import com.snd.snxdbackend.dtos.wishlist.WishlistDTO;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/me")
    public ResponseEntity<WishlistDTO> getWishlist(@AuthenticationPrincipal User user) {
        WishlistDTO wishlistDTO = wishlistService.getWishlistByUserId(user.getId());
        return ResponseEntity.ok(wishlistDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistDTO> addProductToWishlist(@AuthenticationPrincipal User user,
                                                            @RequestParam("productId") Integer productId) {
        WishlistDTO wishlistDTO =  wishlistService.addProductToWishlist(user.getWishlist().getId(), productId);
        return ResponseEntity.ok(wishlistDTO);
    }

    @PostMapping("/remove")
    public ResponseEntity<WishlistDTO> removeProductFromWishlist(@AuthenticationPrincipal User user,@RequestBody Integer productId) {
        WishlistDTO wishlistDTO = wishlistService.removeProductFromWishlist(user.getWishlist().getId(), productId);
        return ResponseEntity.ok(wishlistDTO);
    }


}
