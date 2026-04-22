package com.productServices.repository;

import com.productServices.entity.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ProductSearchRepository extends ElasticsearchRepository<ProductIndex, String> {

    List<ProductIndex> findByNameContaining(String name);
    List<ProductIndex> findByCategory(String category);
}
