package jbar.service_core.Sell.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class SellDTO {
    @NotNull(message = "Error with sell ID")
    private Integer sellId;

    @NotBlank(message = "Error with product name")
    private String product;

    @NotNull(message = "Error with quantity")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Error with total price")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @NotNull(message = "Error with sell date")
    private LocalDateTime sellDate;

    private Boolean status;

    public SellDTO() {
    }

    public SellDTO(Integer sellId, String product, Integer quantity, Double totalPrice, LocalDateTime sellDate, Boolean status) {
        this.sellId = sellId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.status = status;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
