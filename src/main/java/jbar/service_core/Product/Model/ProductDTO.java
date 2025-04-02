package jbar.service_core.Product.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Product DTO")
public class ProductDTO {
    
    @Schema(description = "Unique identifier of the product.", example = "123")
    private Integer productId; // Se agrega el ID para updates
    
    @Schema(description = "Product name.", example = "Laptop")
    @NotNull(message = "Error with product name")
    private String name;

    @Schema(description = "Product description.", example = "A high-performance laptop.")
    @NotNull(message = "Error with product description")
    private String description;

    @Schema(description = "Price of the product.", example = "999.99")
    @NotNull(message = "Error with product price")
    @Positive(message = "Price must be positive")
    private Double price;

    @Schema(description = "Current status of the product.", example = "ACTIVE")
    private Boolean status = true;

    @Schema(description = "List of categories associated with the product.")
    private List<CategoryDTO> productCategories; // Lista de categorías

    @Schema(description = "List of multimedia objects associated with the product.")
    private List<MultimediaDTO> multimedia; // Lista de multimedia

    public ProductDTO() {
    }

    public ProductDTO(Integer productId, String name, String description, Double price, Boolean status, List<CategoryDTO> productCategories, List<MultimediaDTO> multimedia) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.productCategories = productCategories;
        this.multimedia = multimedia;
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

    public List<CategoryDTO> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<CategoryDTO> productCategories) {
        this.productCategories = productCategories;
    }

    public List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaDTO> multimedia) {
        this.multimedia = multimedia;
    }
}
