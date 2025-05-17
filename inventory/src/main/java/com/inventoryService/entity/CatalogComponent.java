package com.inventoryService.entity;
/*
    In the Category class, I implemented a self-referencing relationship to build a category–sub-category hierarchy.

    I used @ManyToOne for the parent field to indicate that each category can have a single parent.

    I used @OneToMany for subCategories to define that one category can have multiple children/subcategories.
    Each Category can optionally have a parent category.
    This helps build a tree-like hierarchy of categories

    This structure allowed me to model real-world nested categories, like 'Electronics > Mobiles > Smartphones'.
    I also added cascade = CascadeType.ALL so when I save or delete a parent category, all its children are automatically handled.
*/

public interface CatalogComponent {

    String getName();
    void print(String prefix);
}
