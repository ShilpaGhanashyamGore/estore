package com.example.estore.repository;

import com.example.estore.entity.ShortDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface ShortDescriptionRepository extends CrudRepository<ShortDescription,Long> {
    public Optional<ShortDescription> findByDescription(String desc);
}
