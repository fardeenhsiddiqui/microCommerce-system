package com.productServices.product.repo;

import com.productServices.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByDeletedDateIsNull();

    List<Product> findByDeletedDateIsNotNull(); // for recycle bin
}
