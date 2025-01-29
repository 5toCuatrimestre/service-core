package jbar.service_core.User_Route.Model;

import jakarta.persistence.*;
import jbar.service_core.User.Model.User;
import jbar.service_core.Route.Model.Route;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_route")
public class UserRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
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

    public UserRoute() {
    }

    public UserRoute(Integer userRouteId, User user, Route route, String notes) {
        this.userRouteId = userRouteId;
        this.user = user;
        this.route = route;
        this.notes = notes;
    }

    public Integer getUserRouteId() {
        return userRouteId;
    }

    public void setUserRouteId(Integer userRouteId) {
        this.userRouteId = userRouteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getNotes() {
        return notes;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
