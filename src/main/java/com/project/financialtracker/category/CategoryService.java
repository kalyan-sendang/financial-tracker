package com.project.financialtracker.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategory(Integer id){
        List<Category>categories = categoryRepository.getCategoriesByUserId(id);
        return categories.stream().map(category -> new CategoryDto(category.getCategoryId(), category.getName(), category.getMaxLimit())).toList();
    }

    public CategoryDto addCategory( Category category){
        Category newCategory = categoryRepository.save(category);
        return new CategoryDto(newCategory.getCategoryId(),newCategory.getName(), newCategory.getMaxLimit());
    }

    public CategoryDto updateCategory(Integer id, Double maxLimit){
        Optional<Category> newCategory = categoryRepository.findById(id);
        if (newCategory.isPresent()) {
            Category category1 = newCategory.get();
            category1.setCategoryId(id);
            category1.setMaxLimit(maxLimit);
            category1.setName(category1.getName());
            return new CategoryDto(category1.getCategoryId(), category1.getName(), category1.getMaxLimit());
        }else{
            return null;
        }
    }
}
