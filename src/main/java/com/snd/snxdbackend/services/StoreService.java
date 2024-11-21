package com.snd.snxdbackend.services;

import com.snd.snxdbackend.converters.StoreConverter;
import com.snd.snxdbackend.converters.UserConverter;
import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.dtos.store.ProductAvailabilityDTO;
import com.snd.snxdbackend.dtos.store.ProductDTO;
import com.snd.snxdbackend.dtos.store.StoreDTO;
import com.snd.snxdbackend.dtos.store.StoreProductDTO;
import com.snd.snxdbackend.dtos.user.UserDTO;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.Product;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.models.store.Store;
import com.snd.snxdbackend.models.store.StoreOwner;
import com.snd.snxdbackend.models.store.StoreProduct;
import com.snd.snxdbackend.models.store.StoreProductId;
import com.snd.snxdbackend.repositories.ProductRepository;
import com.snd.snxdbackend.repositories.StoreProductRepository;
import com.snd.snxdbackend.repositories.StoreRepository;
import com.snd.snxdbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;
    private final UserRepository userRepository;
    private final StoreLocatorService storeLocatorService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, StoreProductRepository storeProductRepository,
                        UserRepository userRepository, StoreLocatorService storeLocatorService, ProductService productService, ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.userRepository = userRepository;
        this.storeLocatorService = storeLocatorService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Transactional
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreConverter.toStore(storeDTO);
        store.setMainOwner(user);
        user.setRole(UserRole.OWNER);
        store = storeRepository.save(store);
        userRepository.save(user);
        return StoreConverter.toStoreDTO(store);
    }

    public StoreDTO getStoreDetails(Integer storeId) {
        return storeRepository.findById(storeId)
                .map(StoreConverter::toStoreDTO)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    @Transactional
    public StoreDTO updateStoreDetails(Integer storeId, StoreDTO updatedStore) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
        if (updatedStore.getName() != null) {
            store.setName(updatedStore.getName());
        }
        if (updatedStore.getAddress() != null) {
            store.setAddress(updatedStore.getAddress());
        }
        return StoreConverter.toStoreDTO(storeRepository.save(store));
    }

    // TODO
    public List<StoreDTO> getAllStores() {
        return null;
    }

    public List<StoreDTO> findAllStoresNearMyLocation(Location location, double distance) {
        return storeRepository.findStoresNearLocation(location.getLongitude(), location.getLatitude(), distance)
                .stream().map(StoreConverter::toStoreDTO).toList();
    }

    public List<StoreProductDTO> getAllProductsInStore(Integer storeId) {
        List<StoreProduct> products = storeProductRepository.findByStoreId(storeId);
        return products.stream().map((s) ->
                new StoreProductDTO(s.getStore().getId(), s.getStore().getName(),
                        s.getProduct().getId(), s.getProduct().getName(), s.getPrice(),
                        s.getStock(), s.getStore().getAddress().toString())).toList();
    }

    public List<UserDTO> addStoreOwner(Integer storeId, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
        StoreOwner owner = new StoreOwner();
        owner.setStore(store);
        owner.setOwner(user);
        store.getOwners().add(owner);
        storeRepository.save(store);
        return store.getOwners().stream().map((u) -> UserConverter.toUserDTO(u.getOwner())).toList();
    }

    public List<ProductAvailabilityDTO> getAvailableProductsInStore(Integer storeId) {
        return storeProductRepository.findByStoreId(storeId).stream()
                .map(storeProduct -> new ProductAvailabilityDTO(
                        storeProduct.getProduct().getId(),
                        storeProduct.getProduct().getName(),
                        storeProduct.getStock(),
                        storeProduct.getProduct().getPrice().doubleValue(),
                        storeProduct.getStore().getAddress()
                ))
                .collect(Collectors.toList());
    }


    public StoreProductDTO addProductToStore(Integer storeId, Integer productId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        StoreProduct storeProduct = new StoreProduct(store, product);
        storeProduct =  storeProductRepository.save(storeProduct);
        return new StoreProductDTO(storeProduct);
    }

    public StoreProductDTO updateProductPriceInStore(Integer productId, Integer storeId , Double price) {
        StoreProductId storeProductId = new StoreProductId(storeId, productId);
        StoreProduct storeProduct = storeProductRepository.findById(storeProductId).orElseThrow(() -> new RuntimeException(""));
        storeProduct.setPrice(price);
        storeProduct = storeProductRepository.save(storeProduct);
        return new StoreProductDTO(storeProduct);
    }

    public StoreProductDTO updateProductStock(Integer productId, Integer storeId, Integer stock) {
        StoreProduct storeProduct = storeProductRepository.findById(new StoreProductId(storeId, productId)).orElseThrow(() -> new RuntimeException("Store Product not found"));
        storeProduct.setStock(stock);
        storeProduct =  storeProductRepository.save(storeProduct);
        return new StoreProductDTO(storeProduct);
    }

    public List<StoreDTO> findAllStoresNearLocation(Location location, double distance, Double rating1, Double rating2) {
        List<StoreDTO> stores = storeRepository.findStoresNearLocation(location.getLongitude(), location.getLatitude(), distance)
                .stream().map(StoreConverter::toStoreDTO).collect(Collectors.toList());
        return filterByRating(stores, rating1, rating2);
    }

    private List<StoreDTO> filterByRating(List<StoreDTO> stores, Double rating1, Double rating2) {
        if (rating1 == null || rating2 == null) return stores;
        return stores.stream()
                .filter(store -> store.getRating() >= rating1 && store.getRating() <= rating2)
                .collect(Collectors.toList());
    }
}
