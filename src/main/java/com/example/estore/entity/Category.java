package com.example.estore.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable= false)
    private String name;

    @Column(name="parent_id")
    private Long parentId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="category")
    private List<Product> relatedProducts;


    public Category(String categoryName, Long catId, Long parId){
        name = categoryName;
        categoryId = catId;
        parentId = parId;
    }
}