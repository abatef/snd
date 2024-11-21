package com.snd.snxdbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snd.snxdbackend.models.store.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, StoreProductId> {

    List<StoreProduct> findByStoreId(Integer store_id);
    List<StoreProduct> findByProductId(Integer product_id);
    Optional<StoreProduct> findByStoreIdAndProductId(Integer store_id, Integer product_id);
    List<StoreProduct> findByProductIdAndStockGreaterThan(Integer product_id, Integer stock);
}
