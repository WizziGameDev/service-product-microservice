package com.product.koptani.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String slug;

    private String name;

    private String description;

    @Column(precision = 15, scale = 2, name = "price_for_member")
    private BigDecimal priceForMember;

    @Column(precision = 15, scale = 2, name = "price_from_mitra")
    private BigDecimal priceFromMitra;

    private Integer stock;

    // satuan produk, misal "kg", "pcs", dll
    private String unit;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "deleted_at")
    private Long deletedAt;
}
