package jbar.service_core.Route.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jbar.service_core.User_Route.Model.UserRoute;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;
import jbar.service_core.Util.Enum.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserRoute> userRoutes;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RoutePositionSiteUser> routePositionSiteUsers;

    public Route() {}

    public Route(Integer routeId, String name, Double distance, Status status) {
        this.routeId = routeId;
        this.name = name;
        this.distance = distance;
        this.status = status;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public List<UserRoute> getUserRoutes() {
        return userRoutes;
    }

    public void setUserRoutes(List<UserRoute> userRoutes) {
        this.userRoutes = userRoutes;
    }

    public List<RoutePositionSiteUser> getRoutePositionSiteUsers() {
        return routePositionSiteUsers;
    }

    public void setRoutePositionSiteUsers(List<RoutePositionSiteUser> routePositionSiteUsers) {
        this.routePositionSiteUsers = routePositionSiteUsers;
    }
}
