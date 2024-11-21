package com.snd.snxdbackend.repositories;

import com.snd.snxdbackend.models.wishlist.WishlistItem;
import com.snd.snxdbackend.models.wishlist.WishlistItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, WishlistItemId> {
    List<WishlistItem> findByWishlistId(Integer wishlist_id);
    Optional<WishlistItem> findWishlistItemById(WishlistItemId wishlistItemId);
    Optional<WishlistItem> findWishlistItemByProductId(Integer product_id);
    List<WishlistItem> findWishlistItemsByProductId(Integer product_id);
}
