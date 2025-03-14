package jbar.service_core.Position.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PositionSite> positionSites;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoutePositionSiteUser> routePositionSiteUsers;

    // 🔹 Constructores
    public Position() {}

    public Position(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // 🔹 Getters y Setters con validaciones
    public Position(Integer positionId) {
        this.positionId = positionId;
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
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        }
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        }
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
