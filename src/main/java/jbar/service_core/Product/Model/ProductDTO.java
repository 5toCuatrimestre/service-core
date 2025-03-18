package jbar.service_core.Product.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Category.Model.CategoryDTO;

import java.util.List;

public class ProductDTO {
    private Integer productId; // Se agrega el ID para updates

    @NotNull(message = "Error with product name")
    private String name;

    @NotNull(message = "Error with product description")
    private String description;

    @NotNull(message = "Error with product price")
    @Positive(message = "Price must be positive")
    private Double price;

    private Boolean status = true;

    private List<CategoryDTO> categories; // Lista de IDs de categorías
    private List<Integer> categoryIds;
    public ProductDTO() {
    }

    public ProductDTO(Integer productId, String name, String description, Double price, Boolean status, List<Integer> categoryIds) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryIds = categoryIds;
    }

    // Getters y Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}