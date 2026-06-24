package com.productServices.category;

import com.productServices.common.domain.CatalogComponent;
import com.productServices.common.entity.SoftDeleteEntity;
import com.productServices.product.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends SoftDeleteEntity implements CatalogComponent{

    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> subCategories = new ArrayList<>();

    @ManyToOne
    private Category parent;

    public Category(String name){
        this.name = name;
    }

    public void addSubCategory(Category sub) {
        sub.setParent(this);
        subCategories.add(sub);
    }

    public void addProduct(Product product) {
        product.setCategory(this);
        products.add(product);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void print(String prefix) {
        System.out.println("Category");
    }

    public Category(){}
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

}
