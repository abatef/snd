package com.snd.snxdbackend.services;

import com.snd.snxdbackend.converters.WishlistConverter;
import com.snd.snxdbackend.dtos.wishlist.WishlistDTO;
import com.snd.snxdbackend.models.Product;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.models.wishlist.Wishlist;
import com.snd.snxdbackend.models.wishlist.WishlistItem;
import com.snd.snxdbackend.models.wishlist.WishlistItemId;
import com.snd.snxdbackend.repositories.ProductRepository;
import com.snd.snxdbackend.repositories.WishlistItemRepository;
import com.snd.snxdbackend.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
    }

    public Wishlist createWishlistForUser(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public WishlistDTO addProductToWishlist(Integer wishlistId, Integer productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WishlistItem wishlistItem = new WishlistItem(product, wishlist);
        wishlistItemRepository.save(wishlistItem);
        return WishlistConverter.toWishlistDTO(wishlist);
    }

    @Transactional
    public WishlistDTO removeProductFromWishlist(Integer wishlistId, Integer productId) {
        WishlistItemId itemId = new WishlistItemId(wishlistId, productId);
        WishlistItem wishlistItem = wishlistItemRepository.findWishlistItemById(itemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        wishlistItemRepository.delete(wishlistItem);
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        return WishlistConverter.toWishlistDTO(wishlist);
    }

    public WishlistDTO getWishlistById(Integer wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        return WishlistConverter.toWishlistDTO(wishlist);
    }

    public WishlistDTO getWishlistByUserId(Integer userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wishlist not found"));
        return WishlistConverter.toWishlistDTO(wishlist);
    }



}
