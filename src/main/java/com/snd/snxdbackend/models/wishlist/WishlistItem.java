package com.snd.snxdbackend.models.wishlist;

import com.snd.snxdbackend.models.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "wishlist_items")
@NoArgsConstructor
public class WishlistItem {
    @SequenceGenerator(name = "wishlist_items_id_gen", sequenceName = "wishlist_id_seq", allocationSize = 1)
    @EmbeddedId
    private WishlistItemId id;

    @MapsId("wishlistId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WishlistItem(Product product, Wishlist wishlist) {
        this.product = product;
        this.wishlist = wishlist;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WishlistItem && id.equals(((WishlistItem) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}