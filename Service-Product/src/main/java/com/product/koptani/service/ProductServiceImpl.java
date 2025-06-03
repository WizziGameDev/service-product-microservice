package com.product.koptani.service;

import com.product.koptani.dto.ProductListResponse;
import com.product.koptani.dto.ProductRequest;
import com.product.koptani.dto.ProductResponse;
import com.product.koptani.entity.Product;
import com.product.koptani.exception.ProductException;
import com.product.koptani.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Cacheable(value = "ProductService.getProducts", key = "'products'")
    public List<ProductListResponse> productList() {
        return productRepository.findAllByDeletedAtIsNull().stream()
                .map(data -> ProductListResponse.builder()
                        .id(data.getId())
                        .slug(data.getSlug())
                        .name(data.getName())
                        .priceForMember(data.getPriceForMember())
                        .priceFromMitra(data.getPriceFromMitra())
                        .stock(data.getStock())
                        .unit(data.getUnit())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "ProductService.getProductById", key = "#id")
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ProductException("Product Not Found"));

        return ProductResponse.builder()
                .id(product.getId())
                .slug(product.getSlug())
                .name(product.getName())
                .description(product.getDescription())
                .priceForMember(product.getPriceForMember())
                .priceFromMitra(product.getPriceFromMitra())
                .stock(product.getStock())
                .unit(product.getUnit())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "ProductService.getProducts", key = "'products'")
    public ProductResponse addProduct(ProductRequest productRequest) {

        Product product = new Product();
        product.setSlug(slugify(productRequest.getName()));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPriceForMember(productRequest.getPriceForMember());
        product.setPriceFromMitra(productRequest.getPriceFromMitra());
        product.setStock(productRequest.getStock());
        product.setUnit(productRequest.getUnit());
        product.setCreatedAt(Instant.now().getEpochSecond());

        Product saveProduct = productRepository.save(product);

        return ProductResponse.builder()
                .id(saveProduct.getId())
                .slug(saveProduct.getSlug())
                .name(saveProduct.getName())
                .description(saveProduct.getDescription())
                .priceForMember(saveProduct.getPriceForMember())
                .priceFromMitra(saveProduct.getPriceFromMitra())
                .stock(saveProduct.getStock())
                .unit(saveProduct.getUnit())
                .createdAt(saveProduct.getCreatedAt())
                .updatedAt(saveProduct.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "ProductService.getProducts", key = "'products'"),
            @CacheEvict(value = "ProductService.getProductById", key = "#id")
    })
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ProductException("Product Not Found"));

        product.setSlug(slugify(productRequest.getName()));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPriceForMember(productRequest.getPriceForMember());
        product.setPriceFromMitra(productRequest.getPriceFromMitra());
        product.setStock(productRequest.getStock());
        product.setUnit(productRequest.getUnit());
        product.setUpdatedAt(Instant.now().getEpochSecond());

        Product saveProduct = productRepository.save(product);

        return ProductResponse.builder()
                .id(saveProduct.getId())
                .slug(saveProduct.getSlug())
                .name(saveProduct.getName())
                .description(saveProduct.getDescription())
                .priceForMember(saveProduct.getPriceForMember())
                .priceFromMitra(saveProduct.getPriceFromMitra())
                .stock(saveProduct.getStock())
                .unit(saveProduct.getUnit())
                .createdAt(saveProduct.getCreatedAt())
                .updatedAt(saveProduct.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "ProductService.getProducts", key = "'products'"),
            @CacheEvict(value = "ProductService.getProductById", key = "#id")
    })
    public String deleteProduct(Integer id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ProductException("Product Not Found"));

        product.setDeletedAt(Instant.now().getEpochSecond());
        productRepository.save(product);

        return "Successfully deleted Product";
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "ProductService.getProducts", key = "'products'"),
            @CacheEvict(value = "ProductService.getProductById", key = "#id")
    })
    public String updateStockProduct(Integer id, Integer stock) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ProductException("Product Not Found"));

        product.setStock(stock);
        productRepository.save(product);
        return "Successfully updated Stock " + product.getName();
    }

    public static String slugify(String input) {
        String baseSlug = input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");

        int randomNumber = new Random().nextInt(9000) + 1000; // random 4 digit antara 1000-9999
        return baseSlug + "-" + randomNumber;
    }
}
