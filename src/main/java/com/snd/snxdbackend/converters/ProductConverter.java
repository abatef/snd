package com.snd.snxdbackend.converters;

import com.snd.snxdbackend.dtos.store.ProductDTO;
import com.snd.snxdbackend.models.Product;

import java.math.BigDecimal;

public class ProductConverter {

    public static ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice().doubleValue());
        dto.setCategory(product.getCategory());
        dto.setSku(product.getSku());
        dto.setBarcode(product.getBarcode());
        return dto;
    }

    public static Product toProduct(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            product.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            product.setCategory(dto.getCategory());
        }
        if (dto.getSku() != null && !dto.getSku().isEmpty()) {
            product.setSku(dto.getSku());
        }
        if (dto.getBarcode() != null && !dto.getBarcode().isEmpty()) {
            product.setBarcode(dto.getBarcode());
        }
        return product;
    }
}
