package jbar.service_core.Position.Model;

import jakarta.persistence.*;
import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

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
    @OneToMany(mappedBy = "position")
    private List<PositionSite> positionSites;

    @OneToMany(mappedBy = "position")
    private List<RoutePositionSiteUser> routePositionSiteUsers;

    public Position() {
    }

    public Position(Integer positionId, String name, String description, Boolean status) {
        this.positionId = positionId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<PositionSite> getPositionSites() {
        return positionSites;
    }

    public void setPositionSites(List<PositionSite> positionSites) {
        this.positionSites = positionSites;
    }

    public List<RoutePositionSiteUser> getRoutePositionSiteUsers() {
        return routePositionSiteUsers;
    }

    public void setRoutePositionSiteUsers(List<RoutePositionSiteUser> routePositionSiteUsers) {
        this.routePositionSiteUsers = routePositionSiteUsers;
    }
}
