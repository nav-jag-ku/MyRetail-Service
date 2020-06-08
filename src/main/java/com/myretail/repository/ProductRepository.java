package com.myretail.repository;

import com.myretail.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, Integer> {

}
