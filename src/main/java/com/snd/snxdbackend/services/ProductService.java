package com.snd.snxdbackend.services;

import com.snd.snxdbackend.converters.ProductConverter;
import com.snd.snxdbackend.dtos.store.ProductAvailabilityDTO;
import com.snd.snxdbackend.dtos.store.ProductDTO;
import com.snd.snxdbackend.models.Product;
import com.snd.snxdbackend.repositories.ProductRepository;
import com.snd.snxdbackend.repositories.StoreProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;
    private final SearchService searchService;


    @Autowired
    public ProductService(ProductRepository productRepository, StoreProductRepository storeProductRepository, SearchService searchService) {
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
        this.searchService = searchService;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductConverter.toProduct(productDTO);
        product = productRepository.save(product);
        searchService.addProduct(product);
        return ProductConverter.toProductDTO(product);
    }

    public List<ProductAvailabilityDTO> getProductAvailability(Integer productId) {
        return storeProductRepository.findByProductId(productId).stream()
                .map(storeProduct -> new ProductAvailabilityDTO(
                        storeProduct.getStore().getId(),
                        storeProduct.getStore().getName(),
                        storeProduct.getStock(),
                        storeProduct.getProduct().getPrice().doubleValue(),
                        storeProduct.getStore().getAddress()
                ))
                .collect(Collectors.toList());
    }


    public ProductDTO getProduct(Integer productId) {
        return ProductConverter.toProductDTO(Objects.requireNonNull(productRepository.findById(productId).orElse(null)));
    }

    public ProductDTO updateProduct(Integer id ,ProductDTO productDTO) {
        Product product = ProductConverter.toProduct(productDTO);
        product.setId(id);
        product = productRepository.save(product);
        return ProductConverter.toProductDTO(product);
    }



}
