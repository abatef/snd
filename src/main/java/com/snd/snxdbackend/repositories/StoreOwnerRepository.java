package com.snd.snxdbackend.repositories;

import com.snd.snxdbackend.models.store.Store;
import com.snd.snxdbackend.models.store.StoreOwner;
import com.snd.snxdbackend.models.store.StoreOwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreOwnerRepository extends JpaRepository<StoreOwner, StoreOwnerId> {
    Optional<StoreOwner> findStoreOwnerById(StoreOwnerId id);
    Optional<StoreOwner> findStoreOwnerByOwnerId(Integer ownerId);
    Optional<StoreOwner> findStoreOwnerByStoreId(Integer storeId);
}
