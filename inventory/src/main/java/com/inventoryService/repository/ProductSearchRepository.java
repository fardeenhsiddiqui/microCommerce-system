package com.inventoryService.repository;

import com.inventoryService.entity.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
public interface ProductSearchRepository extends ElasticsearchRepository<ProductIndex, String> {

    List<ProductIndex> findByNameContaining(String name);
    List<ProductIndex> findByCategory(String category);
}
