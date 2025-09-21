package com.gmontinny.springbootaiopenaitensorflow.repository;

import com.gmontinny.springbootaiopenaitensorflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}