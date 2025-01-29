package jbar.service_core.Site.Model;

import jakarta.persistence.*;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer siteId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "VARCHAR(255)")
    private String location;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PositionSite> positionSites;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutePositionSiteUser> routePositionSiteUsers;
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

    // Constructor vacío
    public Site() {
    }

    // Constructor con todos los campos
    public Site(Integer siteId, String name, String location, Boolean status, Company company) {
        this.siteId = siteId;
        this.name = name;
        this.location = location;
        this.status = status;
        this.company = company;
    }

    // Getters y setters
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
