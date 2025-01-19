package jbar.service_core.Sell.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sell")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;

    @Column(name = "product", columnDefinition = "VARCHAR(100)", nullable = false)
    private String product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "totalPrice", nullable = false)
    private Double totalPrice;

    @Column(name = "sellDate", nullable = false)
    private LocalDateTime sellDate;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    public Sell() {
    }

    public Sell(Integer sellId, String product, Integer quantity, Double totalPrice, LocalDateTime sellDate, Boolean status) {
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
