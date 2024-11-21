package com.snd.snxdbackend.controllers;

import com.snd.snxdbackend.dtos.Location;
import com.snd.snxdbackend.dtos.store.StoreDTO;
import com.snd.snxdbackend.dtos.store.StoreProductDTO;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/")
    public ResponseEntity<StoreDTO> createStore(@AuthenticationPrincipal User user,
                                                @RequestBody StoreDTO storeDTO) {
        StoreDTO createdStore = storeService.createStore(storeDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{id}/info")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<StoreDTO> updateStore(@AuthenticationPrincipal User user,
                                                @RequestBody StoreDTO storeDTO,
                                                @PathVariable("id") Integer storeId) {
        if (user.getOwnedStores().stream().noneMatch(store -> Objects.equals(store.getId(), storeId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        StoreDTO updatedStore = storeService.updateStoreDetails(storeId, storeDTO);
        return ResponseEntity.ok(updatedStore);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<StoreDTO> getStore(@PathVariable("id") Integer storeId) {
        StoreDTO storeDTO = storeService.getStoreDetails(storeId);
        return ResponseEntity.ok(storeDTO);
    }

    @GetMapping()
    public ResponseEntity<List<StoreDTO>> getAllStoresBy(
            @RequestParam(required = false) Double rating1,
            @RequestParam(required = false) Double rating2,
            @RequestBody(required = false) Location location,
            @RequestParam(defaultValue = "1000") Integer distance,
            @AuthenticationPrincipal User user) {
        Location userLocation = location != null ? location : Location.of(user.getPreferredLocation());
        List<StoreDTO> stores = storeService.findAllStoresNearLocation(userLocation, distance, rating1, rating2);
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/{storeId}/products")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<StoreProductDTO> addProductToStore(@PathVariable("storeId") Integer storeId,
                                                             @RequestParam("productId") Integer productId) {
        StoreProductDTO dto = storeService.addProductToStore(storeId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}/stock")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<StoreProductDTO> updateProductStock(@PathVariable("id") Integer storeId,
                                                              @RequestParam("product") Integer productId,
                                                              @RequestParam("stock") Integer stock) {
        StoreProductDTO dto = storeService.updateProductStock(storeId, productId, stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("{id}/price")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<StoreProductDTO> updateProductPrice(@PathVariable("id") Integer storeId,
                                                              @RequestParam("product") Integer productId,
                                                              @RequestParam("price") Double price) {
        StoreProductDTO dto = storeService.updateProductPriceInStore(productId, storeId, price);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<StoreProductDTO>> getProductsByStore(@PathVariable("id") Integer storeId) {
        List<StoreProductDTO> product = storeService.getAllProductsInStore(storeId);
        return ResponseEntity.ok(product);
    }
}
