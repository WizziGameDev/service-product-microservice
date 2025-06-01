package com.product.koptani.resolver;

import com.product.koptani.dto.ProductListResponse;
import com.product.koptani.dto.ProductRequest;
import com.product.koptani.dto.ProductResponse;
import com.product.koptani.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Controller
public class ProductResolver {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @QueryMapping
    public List<ProductListResponse> products() {
        return runVirtual(() -> productServiceImpl.productList());
    }

    @QueryMapping
    public ProductResponse productById(@Argument Integer id) {
        return runVirtual(() -> productServiceImpl.getProductById(id));
    }

    @MutationMapping
    public ProductResponse createProduct(@Argument("input") @Valid ProductRequest productRequest) {
        return runVirtual(() -> productServiceImpl.addProduct(productRequest));
    }

    @MutationMapping
    public ProductResponse updateProduct(@Argument Integer id,
                                         @Argument("input") @Valid ProductRequest productRequest) {
        return runVirtual(() -> productServiceImpl.updateProduct(id, productRequest));
    }

    @MutationMapping
    public String deleteProduct(@Argument Integer id) {
        return runVirtual(() -> productServiceImpl.deleteProduct(id));
    }

    @MutationMapping
    public String updateStockProduct(@Argument Integer id, @Argument Integer stock) {
        return runVirtual(() -> productServiceImpl.updateStockProduct(id, stock));
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
