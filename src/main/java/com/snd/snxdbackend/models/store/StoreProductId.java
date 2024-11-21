package com.snd.snxdbackend.models.store;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class StoreProductId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1717204810009826731L;
    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public StoreProductId(Integer storeId, Integer productId) {
        this.storeId = storeId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StoreProductId entity = (StoreProductId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.storeId, entity.storeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, storeId);
    }

}