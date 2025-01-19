package jbar.service_core.Product.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDTO {
    @NotNull(message = "Error with product ID")
    private Integer productId;

    @NotBlank(message = "Error with product name")
    private String name;

    @NotBlank(message = "Error with product description")
    private String description;

    @Positive(message = "Error with product price")
    private Double price;

    private Boolean status;

    public ProductDTO() {
    }

    public ProductDTO(Integer productId, String name, String description, Double price, Boolean status) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

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
}
