package com.next.app.api.product.service;

import com.next.app.api.product.dto.ProductSummaryDto;
import com.next.app.api.product.entity.Product;
import com.next.app.api.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductSummaryDto> searchByNameSimple(String q) {
        if (q == null || q.isBlank()) return Collections.emptyList();
        List<Product> rows = productRepository
                .findTop50ByNameContainingIgnoreCaseOrderByIdDesc(q.trim());
        return rows.stream().map(ProductSummaryDto::from).toList();
    }

    public ProductSummaryDto getById(Long id) {
        return productRepository.findById(id)
                .map(ProductSummaryDto::from)
                .orElse(null);
    }
}
