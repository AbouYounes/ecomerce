package de.ecom.ecomapplication.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private Integer quantity;
}
