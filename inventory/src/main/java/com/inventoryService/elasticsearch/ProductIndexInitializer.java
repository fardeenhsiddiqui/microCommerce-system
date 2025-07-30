package com.inventoryService.elasticsearch;

import com.inventoryService.entity.ProductIndex;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
