package com.example.estore.repository;

import com.example.estore.entity.LongDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongDescriptionRepository extends CrudRepository<LongDescription,Long> {

}
