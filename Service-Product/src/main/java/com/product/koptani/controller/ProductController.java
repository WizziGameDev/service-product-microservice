package com.product.koptani.controller;

import com.product.koptani.dto.ProductListResponse;
import com.product.koptani.dto.ProductRequest;
import com.product.koptani.dto.ProductResponse;
import com.product.koptani.dto.WebResponse;
import com.product.koptani.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping(
            value = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductListResponse>> getAllProducts() {
        return runVirtual(() -> WebResponse.<List<ProductListResponse>>builder()
                .statusCode(200)
                .data(productServiceImpl.productList())
                .errors(null)
                .build());
    }

    @GetMapping(
            value = "/product/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> getProductById(@PathVariable(value = "id") Integer id) {
        return runVirtual(() -> WebResponse.<ProductResponse>builder()
                .statusCode(200)
                .data(productServiceImpl.getProductById(id))
                .errors(null)
                .build());
    }

    @PostMapping(
            value = "/product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return runVirtual(() -> WebResponse.<ProductResponse>builder()
                .statusCode(200)
                .data(productServiceImpl.addProduct(productRequest))
                .errors(null)
                .build());
    }

    @PutMapping(
            value = "/product/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> updateProduct(@PathVariable(value = "id") Integer id,
                                                      @RequestBody @Valid ProductRequest productRequest) {
        return runVirtual(() -> WebResponse.<ProductResponse>builder()
                .statusCode(200)
                .data(productServiceImpl.updateProduct(id, productRequest))
                .errors(null)
                .build());
    }

    @DeleteMapping(
            value = "/product/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteProduct(@PathVariable(value = "id") Integer id) {
        return runVirtual(() -> WebResponse.<String>builder()
                .statusCode(200)
                .data(productServiceImpl.deleteProduct(id))
                .errors(null)
                .build());
    }

    private <T> T runVirtual(Supplier<T> task) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        Thread.startVirtualThread(() -> {
            try {
                result.complete(task.get());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });

        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
