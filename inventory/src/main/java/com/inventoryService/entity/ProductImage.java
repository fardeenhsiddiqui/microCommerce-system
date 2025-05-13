package com.inventoryService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String imageUrl;
    private String label;

    @ManyToOne
    private Product product;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate deletedDate;
    private String deletedBy;

}
