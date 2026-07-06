package com.productServices.category.repo;

import com.productServices.category.Category;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // Get all subcategories for a given category
    List<Category> findByParentIdAndDeletedDateIsNull(@Param("parentId") UUID parentId);

    List<Category> findByDeletedDateIsNull();

    // Get root categories (top-level ones with no parent)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.deletedDate IS NULL")
    List<Category> findTopLevelCategories();


    List<Category> findByParentIsNullAndDeletedDateIsNull();
}
