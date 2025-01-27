package jbar.service_core.Route_Position_Site_User.Model;

import jakarta.validation.constraints.NotNull;

public class RoutePositionSiteUserDTO {
    @NotNull(message = "Error with route ID")
    private Integer routeId;

    @NotNull(message = "Error with position ID")
    private Integer positionId;

    @NotNull(message = "Error with site ID")
    private Integer siteId;

    @NotNull(message = "Error with user ID")
    private Integer userId;

    public RoutePositionSiteUserDTO() {
    }

    public RoutePositionSiteUserDTO(Integer routeId, Integer positionId, Integer siteId, Integer userId) {
        this.routeId = routeId;
        this.positionId = positionId;
        this.siteId = siteId;
        this.userId = userId;
    }

    // Getters y Setters

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
