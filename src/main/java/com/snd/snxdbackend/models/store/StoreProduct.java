package com.snd.snxdbackend.models.store;

import com.snd.snxdbackend.models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "stores_product")
@AllArgsConstructor
@NoArgsConstructor
public class StoreProduct {
    @SequenceGenerator(name = "stores_product_id_gen", sequenceName = "stores_id_seq", allocationSize = 1)
    @EmbeddedId
    private StoreProductId id;

    public StoreProduct(Store store, Product product) {
        this.id = new StoreProductId(store.getId(), product.getId());
        this.product = product;
        this.store = store;
    }

    @MapsId("storeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ColumnDefault("0")
    @Column(name = "price", nullable = false)
    private Double price;

    @Override
    public boolean equals(Object o) {
        return o instanceof StoreProduct && id.equals(((StoreProduct) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}