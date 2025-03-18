package jbar.service_core.Product_Category.Model;

import jakarta.validation.constraints.NotNull;

public class ProductCategoryDTO {
    private Integer productCategoryId; // Se agrega el ID para updates

    @NotNull(message = "Error with product ID")
    private Integer productId;

    @NotNull(message = "Error with category ID")
    private Integer categoryId;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(Integer productCategoryId, Integer productId, Integer categoryId) {
        this.productCategoryId = productCategoryId;
        this.productId = productId;
        this.categoryId = categoryId;
    }

    // Getters y Setters

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}