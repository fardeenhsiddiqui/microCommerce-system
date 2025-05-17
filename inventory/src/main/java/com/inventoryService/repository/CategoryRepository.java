package com.inventoryService.repository;

import com.inventoryService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // Get all subcategories for a given category
    List<Category> findByParentId(@Param("parentId") UUID parentId);

    // Get root categories (top-level ones with no parent)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findTopLevelCategories();


}
