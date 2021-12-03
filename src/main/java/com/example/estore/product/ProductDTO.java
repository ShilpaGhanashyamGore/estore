package com.example.estore.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {
    @NotNull(message="product id is not optional")
    private Long productId;
    @NotBlank(message="product name is not optional")
    private String productName;
    @NotNull(message="is active is not optional")
    private Boolean isActive;
    @NotBlank(message="short description is not optional")
    private String shortDescription;
    @NotBlank(message="long description is not optional")
    private String longDescription;
    @NotEmpty(message="category is not optional")
    private String category;
}
