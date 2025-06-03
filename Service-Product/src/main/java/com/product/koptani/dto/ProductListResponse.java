package com.product.koptani.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private Integer id;

    private String slug;

    private String name;

    private BigDecimal priceForMember;

    private BigDecimal priceFromMitra;

    private Integer stock;

    private String unit;

}
