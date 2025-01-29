package jbar.service_core.Sell_Detail.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SellDetailDTO {
    private Integer sellDetailId; // Se agrega el ID para updates

    @NotNull(message = "Error with sell ID")
    private Integer sellId;

    @NotNull(message = "Error with product ID")
    private Integer productId;

    @NotNull(message = "Error with quantity")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Error with unit price")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;

    @NotNull(message = "Error with total price")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    public SellDetailDTO() {
    }

    public SellDetailDTO(Integer sellDetailId, Integer sellId, Integer productId, Integer quantity, Double unitPrice, Double totalPrice) {
        this.sellDetailId = sellDetailId;
        this.sellId = sellId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // Getters y Setters

    public Integer getSellDetailId() {
        return sellDetailId;
    }

    public void setSellDetailId(Integer sellDetailId) {
        this.sellDetailId = sellDetailId;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

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
}
