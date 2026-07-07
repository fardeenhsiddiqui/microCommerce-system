package com.productServices.product.mapper;

import com.productServices.category.Category;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductIndexDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.dto.UpdateProductDTO;
import com.productServices.product.enums.ProductStatus;
import com.productServices.productImage.dto.ImageResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(CreateProductDTO dto,
                            Category category) {

        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock() == null ? 0L : dto.stock());
        product.setCategory(category);
        product.setProductStatus(ProductStatus.ACTIVE);

        return product;
    }

    public ProductResponseDTO toResponse(Product product) {

        List<ImageResponseDTO> images = product.getGalleryImages()
                        .stream()
                        .map(img -> new ImageResponseDTO(
                                img.getImageUrl(),
                                img.getLabel(),
                                img.getAltText()))
                        .toList();

        return new ProductResponseDTO(
                product,
                images
        );
    }

    public ProductIndex toIndex(Product product) {

        ProductIndex index = new ProductIndex();

        index.setId(product.getId().toString());
        index.setName(product.getName());
        index.setDescription(product.getDescription());
        index.setCategory(product.getCategory().getName());
        index.setPrice(product.getPrice());

        return index;
    }

    public void updateEntity(Product product,
                             UpdateProductDTO dto,
                             Category category){

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCategory(category);
    }

    public ProductIndexDTO productIndexDTO(ProductIndex index) {

        return new ProductIndexDTO(
                index.getName(),
                index.getDescription(),
                index.getCategory(),
                index.getPrice()
        );
    }
}
