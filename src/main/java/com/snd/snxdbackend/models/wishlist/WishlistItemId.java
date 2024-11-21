package com.snd.snxdbackend.models.wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 6836062730433986396L;
    @Column(name = "wishlist_id", nullable = false)
    private Integer wishlistId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WishlistItemId entity = (WishlistItemId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.wishlistId, entity.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, wishlistId);
    }

}