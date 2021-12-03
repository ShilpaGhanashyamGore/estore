package com.example.estore.repository;

import com.example.estore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);
    Optional<Category> findByCategoryId(long categoryId);
    List<Category> findAllByParentId(long categoryId);
}
