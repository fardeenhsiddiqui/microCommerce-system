package com.productServices.product.service;

import com.productServices.product.Product;
import com.productServices.product.dto.ProductFilter;
import com.productServices.product.enums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;


public final class ProductSpecification {

    public static Specification<Product> hasCategory(UUID categoryId) {

        return (root, query, cb) ->
                categoryId == null
                        ? null
                        : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasStatus(ProductStatus status) {

        return (root, query, cb) ->
                status == null
                        ? null
                        : cb.equal(root.get("productStatus"), status);
    }

    public static Specification<Product> minPrice(Double price) {

        return (root, query, cb) ->
                price == null
                        ? null
                        : cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> maxPrice(Double price) {

        return (root, query, cb) ->
                price == null
                        ? null
                        : cb.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> notDeleted() {

        return (root, query, cb) ->
                cb.isNull(root.get("deletedDate"));
    }

    public static Specification<Product> withFilter(ProductFilter filter) {

        Specification<Product> specification = ProductSpecification.notDeleted();

        if (filter.categoryId() != null) {
            specification = specification.and(
                    ProductSpecification.hasCategory(filter.categoryId()));
        }

        if (filter.status() != null) {
            specification = specification.and(
                    ProductSpecification.hasStatus(filter.status()));
        }

        if (filter.minPrice() != null) {
            specification = specification.and(
                    ProductSpecification.minPrice(filter.minPrice()));
        }

        if (filter.maxPrice() != null) {
            specification = specification.and(
                    ProductSpecification.maxPrice(filter.maxPrice()));
        }

        return specification;
    }

}
