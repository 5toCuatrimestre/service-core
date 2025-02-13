package jbar.service_core.Sell.Model;

import jakarta.persistence.*;
import jbar.service_core.User.Model.User;
import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import jbar.service_core.Sell_Detail.Model.SellDetail;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sell")
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "sell_date", nullable = false)
    private LocalDateTime sellDate;

    @Column(name = "sell_time", nullable = false)
    private LocalTime sellTime;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Relación con RatingUserSell (Para registrar calificaciones de meseros)
    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingUserSell> ratings;

    // Relación con SellDetail (Para almacenar los productos vendidos en esta venta)
    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SellDetail> sellDetails;

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalTime getSellTime() {
        return sellTime;
    }

    public void setSellTime(LocalTime sellTime) {
        this.sellTime = sellTime;
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

    public List<RatingUserSell> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingUserSell> ratings) {
        this.ratings = ratings;
    }

    public List<SellDetail> getSellDetails() {
        return sellDetails;
    }

    public void setSellDetails(List<SellDetail> sellDetails) {
        this.sellDetails = sellDetails;
    }
}
