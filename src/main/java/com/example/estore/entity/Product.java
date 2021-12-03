package com.example.estore.entity;

import com.example.estore.entity.Category;
import com.example.estore.entity.LongDescription;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name="product_id", nullable = false)
    private Long productId;

    @Column(name= "product_name", nullable = false)
    private String name;

    @Column(name="online_status", nullable=false)
    private Boolean isActive;

    @ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id",nullable = true)
    @EqualsAndHashCode.Exclude
    private Category category;

    @ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="long_description_id",nullable = true)
    @EqualsAndHashCode.Exclude
    private LongDescription longDescription;

    @ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="short_description_id",nullable = true)
    @EqualsAndHashCode.Exclude
    private ShortDescription shortDescription;
}
