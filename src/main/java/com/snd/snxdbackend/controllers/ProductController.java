package com.snd.snxdbackend.controllers;

import com.snd.snxdbackend.dtos.store.ProductDTO;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.services.ProductService;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.isEnabled()")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO dto = productService.createProduct(productDTO);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.isEnabled()")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
        ProductDTO dto = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Integer id) {
        ProductDTO dto = productService.getProduct(id);
        return ResponseEntity.ok().body(dto);
    }



}
