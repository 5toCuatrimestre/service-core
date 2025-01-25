package jbar.service_core.Sell.Model;

import jakarta.persistence.*;
import jbar.service_core.Product.Model.Product;

import java.time.LocalDateTime;

@Entity
@Table(name = "sell")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "sell_date", nullable = false)
    private LocalDateTime sellDate;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    // Constructor por defecto
    public Sell() {
    }

    // Constructor con parámetros
    public Sell(Integer sellId, Product product, Integer quantity, Double totalPrice, LocalDateTime sellDate, Boolean status) {
        this.sellId = sellId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.status = status;
    }

    // Getters y Setters
    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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
