package com.example.estore.entity;

import com.example.estore.entity.Product;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="short_description")
@NoArgsConstructor
public class ShortDescription {

    @Id
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "short_description_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "shortDescription")
    private List<Product> products;

    public ShortDescription(String desc){
        description = desc;
    }
}