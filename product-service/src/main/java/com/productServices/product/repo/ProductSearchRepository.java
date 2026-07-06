package com.productServices.product.repo;

import com.productServices.product.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductIndex, String> {

    List<ProductIndex> findByNameContaining(String name);
    List<ProductIndex> findByCategory(String category);
}
