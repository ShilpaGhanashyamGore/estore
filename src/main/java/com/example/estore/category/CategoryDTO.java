package com.example.estore.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDTO {
    @NotNull(message="category id is not optional")
    private Long categoryId;
    @NotBlank(message="category name is not optional")
    private String categoryName;
    @NotBlank(message="parent id is not optional")
    private String parentCategory;
}
