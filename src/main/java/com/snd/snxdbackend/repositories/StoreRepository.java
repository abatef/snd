package com.snd.snxdbackend.repositories;

import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.models.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findStoreByAddress_City(String city);
    List<Store> findStoreByMainOwnerId(Integer mainOwnerId);
    List<Store> findByRatingBetween(BigDecimal rating, BigDecimal rating2);

    @Query(value = "SELECT s FROM Store s where ST_DistanceSphere(s.location, ST_MakePoint(:lng, :lat)) <= :distance")
    List<Store> findStoresNearLocation(double lat, double lng, double distance);


}
