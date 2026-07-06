package com.productServices.product.service;

import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.enums.ProductStatus;
import com.productServices.product.event.ProductRestockedEvent;
import com.productServices.product.publisher.RestockEventPublisher;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import com.productServices.productImage.dto.ImageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
}
