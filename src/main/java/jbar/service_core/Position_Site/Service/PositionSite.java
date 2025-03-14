package jbar.service_core.Position_Site.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;
import jbar.service_core.Site.Model.Site;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "position_site")
public class PositionSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionSiteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    @JsonIgnore
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    @JsonIgnore
    private Site site;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "x_location", nullable = false)
    private Double xLocation;

    @Column(name = "y_location", nullable = false)
    private Double yLocation;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
 @JsonIgnore
    @OneToMany(mappedBy = "positionSite", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RoutePositionSiteUser> routePositionSiteUsers;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public PositionSite() {}

    public PositionSite(Position position, Site site, Integer capacity, Double xLocation, Double yLocation) {
        this.position = position;
        this.site = site;
        this.capacity = capacity;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    // 🔹 Getters y Setters
    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getXLocation() {
        return xLocation;
    }

    public void setXLocation(Double xLocation) {
        this.xLocation = xLocation;
    }

    public Double getYLocation() {
        return yLocation;
    }

    public void setYLocation(Double yLocation) {
        this.yLocation = yLocation;
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

    public List<RoutePositionSiteUser> getRoutePositionSiteUsers() {
        return routePositionSiteUsers;
    }

    public void setRoutePositionSiteUsers(List<RoutePositionSiteUser> routePositionSiteUsers) {
        this.routePositionSiteUsers = routePositionSiteUsers;
    }
}
