package jbar.service_core.RatingUserSell.Model;

import jakarta.persistence.*;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.User.Model.User;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_user_sell")
public class RatingUserSell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingUserSellId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id", nullable = false)
    private Sell sell;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date(System.currentTimeMillis());  // Usar Date

    @Column(name = "updated_at")
    private Date updatedAt;  // Cambiar a Date

    @Column(name = "deleted_at")
    private Date deletedAt;  // Cambiar a Date

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date(System.currentTimeMillis());  // Establecer la fecha de actualización
    }


    // Constructores
    public RatingUserSell() {}

    public RatingUserSell(User user, Sell sell, Integer stars) {
        this.user = user;
        this.sell = sell;
        this.stars = stars;
    }

    // Getters y Setters
    public Integer getRatingUserSellId() {
        return ratingUserSellId;
    }

    public void setRatingUserSellId(Integer ratingUserSellId) {
        this.ratingUserSellId = ratingUserSellId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
