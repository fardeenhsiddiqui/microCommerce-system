package com.inventoryService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String categoryName;
    private String description;

    @ManyToOne
    private Product product;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate deletedDate;
    private String deletedBy;
}
