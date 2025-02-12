package jbar.service_core.Product.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Product DTO")
public class ProductDTO {

    @NotBlank(message = "Product name cannot be blank.")
    private String name;

    @NotBlank(message = "Product description cannot be blank.")
    private String description;

    @Positive(message = "Product price must be greater than zero.")
    private Double price;

    private Boolean status;

    public ProductDTO() {
    }

    public ProductDTO(String name, String description, Double price, Boolean status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
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
