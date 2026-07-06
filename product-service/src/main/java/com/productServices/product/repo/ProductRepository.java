package com.productServices.product.repo;

import com.productServices.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Page<Product> findByDeletedDateIsNull(Pageable pageable);

    List<Product> findByDeletedDateIsNull();

    List<Product> findByDeletedDateIsNotNull(); // for recycle bin
}
