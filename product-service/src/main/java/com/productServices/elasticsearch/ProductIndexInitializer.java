package com.productServices.elasticsearch;

import com.productServices.entity.ProductIndex;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "product.search.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class ProductIndexInitializer {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProductIndexInitializer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostConstruct
    public void createIndexIfNotExists() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(ProductIndex.class);
        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
        }
    }
}
