package jbar.service_core.Sell.Model;

import jakarta.persistence.*;
import jbar.service_core.Sell_Detail.Model.SellDetail;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sell")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "sell_date", nullable = false)
    private LocalDateTime sellDate;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SellDetail> sellDetails; // Relación con SellDetail

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructor por defecto
    public Sell() {
    }

    // Constructor con parámetros
    public Sell(Integer sellId, Double totalPrice, LocalDateTime sellDate, Boolean status) {
        this.sellId = sellId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<SellDetail> getSellDetails() {
        return sellDetails;
    }

    public void setSellDetails(List<SellDetail> sellDetails) {
        this.sellDetails = sellDetails;
    }
}
