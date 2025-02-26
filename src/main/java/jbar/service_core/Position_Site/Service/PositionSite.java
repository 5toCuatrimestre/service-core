package jbar.service_core.Position_Site.Service;

import jakarta.persistence.*;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Site.Model.Site;

import java.time.LocalDateTime;

@Entity
@Table(name = "position_site")
public class PositionSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionSiteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "x_location", precision = 8, scale = 6, nullable = false)
    private Double xLocation;

    @Column(name = "y_location", precision = 8, scale = 6, nullable = false)
    private Double yLocation;

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

    public PositionSite() {
    }

    public PositionSite(Integer positionSiteId, Position position, Site site, Integer capacity, Double xLocation, Double yLocation) {
        this.positionSiteId = positionSiteId;
        this.position = position;
        this.site = site;
        this.capacity = capacity;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

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
}
