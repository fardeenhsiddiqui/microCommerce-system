package com.productServices.product.repo;

import com.productServices.product.ProductIndex;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductIndex, String> {

    List<ProductIndex> findByNameContaining(String name);
    List<ProductIndex> findByCategory(String category);

    @Query("""
        {
          "multi_match": {
            "query": "?0",
            "fields": [
              "name^3",
              "description"
            ],
            "type": "best_fields",
            "operator": "or",
            "fuzziness": "AUTO"
          }
        }
    """)
    List<ProductIndex> search(String keyword);


    List<ProductIndex> findByNameContainingOrDescriptionContaining(String name, String description);
}
