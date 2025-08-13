package com.example.next.app.api.product.repository;

import com.example.next.app.api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop50ByNameContainingIgnoreCaseOrderByIdDesc(String q);
}
