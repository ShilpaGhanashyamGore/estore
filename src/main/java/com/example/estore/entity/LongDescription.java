package com.example.estore.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="long_description")
public class LongDescription {

    @Id
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "long_description_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "longDescription")
    private List<Product> products;

    public LongDescription(String longDesc){
        description = longDesc;
    }
}
