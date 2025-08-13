package com.example.next.app.api.category.service;

import com.example.next.app.api.category.controller.dto.CategoryDto;
import com.example.next.app.api.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repo;
    public CategoryService(CategoryRepository repo) { this.repo = repo; }

    public List<CategoryDto> list() {
        return repo.findAll().stream()
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
    }
}