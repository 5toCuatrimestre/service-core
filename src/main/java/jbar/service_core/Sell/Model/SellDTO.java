package jbar.service_core.Sell.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class SellDTO {
    @NotNull(message = "Error with product ID")
    private Integer productId;

    @NotNull(message = "Error with quantity")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Error with total price")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @NotNull(message = "Error with sell date")
    private LocalDateTime sellDate;

    @NotNull(message = "Status is required")
    private Boolean status;

    public SellDTO() {
    }

    public SellDTO(Integer productId, Integer quantity, Double totalPrice, LocalDateTime sellDate, Boolean status) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.status = status;
    }

    // Getters y Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDateTime sellDate) {
        this.sellDate = sellDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
