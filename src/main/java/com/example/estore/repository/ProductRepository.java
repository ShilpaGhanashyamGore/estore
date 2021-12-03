package com.example.estore.repository;

import com.example.estore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Optional<Product> findByProductId(long productId);
    Optional<Product> findByName(String productName);
}
