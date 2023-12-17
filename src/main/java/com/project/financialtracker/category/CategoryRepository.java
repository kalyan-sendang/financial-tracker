package com.project.financialtracker.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select * from category where user_id = :userId ",nativeQuery = true)
    List<Category> getCategoriesByUserId(Integer userId);

    @Query(value = "select * from category where category_id = :categoryId ",nativeQuery = true)
    Category findByCategoryId(Integer categoryId);


    @Query(value = "select * from category where user_id = :userId and name ILIKE '%' || :name || '%'", nativeQuery = true)
    List<Category> getCategoriesByUserIdAndName(Integer userId, String name);
}
