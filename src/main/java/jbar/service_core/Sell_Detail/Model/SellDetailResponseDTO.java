package jbar.service_core.Sell_Detail.Model;

import jakarta.validation.constraints.NotNull;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Multimedia.Model.MultimediaDTO;

import java.util.List;

public class SellDetailResponseDTO {
    @NotNull(message = "Sell Detail ID is required")
    private Integer sellDetailId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product description is required")
    private String description;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    private Double unitPrice;

    @NotNull(message = "Total price is required")
    private Double totalPrice;

    private List<CategoryDTO> productCategories;
    private List<MultimediaDTO> multimedia;

    public SellDetailResponseDTO() {
    }

    public SellDetailResponseDTO(Integer sellDetailId, Integer productId, String productName, String description, 
            Integer quantity, Double unitPrice, Double totalPrice, List<CategoryDTO> productCategories, 
            List<MultimediaDTO> multimedia) {
        this.sellDetailId = sellDetailId;
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.productCategories = productCategories;
        this.multimedia = multimedia;
    }

    public Integer getSellDetailId() {
        return sellDetailId;
    }

    public void setSellDetailId(Integer sellDetailId) {
        this.sellDetailId = sellDetailId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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