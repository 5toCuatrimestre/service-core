package jbar.service_core.Sell.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jbar.service_core.User.Model.User;
import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import jbar.service_core.Sell_Detail.Model.SellDetail;

import java.sql.Date;  // Usamos java.sql.Date para representar la fecha
import java.sql.Time;  // Usamos java.sql.Time para representar el tiempo
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "sell")
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "position_site_id", nullable = true)
    private Integer positionSiteId;

    @Column(name = "sell_date", nullable = false)
    private Timestamp sellDate;  // Usamos java.sql.Timestamp para la fecha y hora completas

    @Column(name = "sell_time", nullable = false)
    private Time sellTime;  // Usamos java.sql.Time para el tiempo

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());  // Timestamp actual

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());  // Actualizamos a la hora actual
    }
    // Relación con RatingUserSell (Para registrar calificaciones de meseros)
    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RatingUserSell> ratings;

    // Relación con SellDetail (Para almacenar los productos vendidos en esta venta)
    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SellDetail> sellDetails;

    public Sell() {
    }

    public Sell(Integer sellId, User user, Double totalPrice, Integer position_site_id, Timestamp sellDate, Time sellTime, Boolean status, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, List<RatingUserSell> ratings, List<SellDetail> sellDetails) {
        this.sellId = sellId;
        this.user = user;
        this.totalPrice = totalPrice;
        this.positionSiteId = position_site_id;
        this.sellDate = sellDate;
        this.sellTime = sellTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.ratings = ratings;
        this.sellDetails = sellDetails;
    }

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

    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public Time getSellTime() {
        return sellTime;
    }

    public void setSellTime(Time sellTime) {
        this.sellTime = sellTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Timestamp getSellDate() {
        return sellDate;
    }

    public void setSellDate(Timestamp sellDate) {
        this.sellDate = sellDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
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
