package jbar.service_core.User_Route.Model;

import jakarta.validation.constraints.NotNull;

public class UserRouteDTO {
    @NotNull(message = "Error with user ID")
    private Integer userId;

    @NotNull(message = "Error with route ID")
    private Integer routeId;

    private String notes;

    public UserRouteDTO() {
    }

    public UserRouteDTO(Integer userId, Integer routeId, String notes) {
        this.userId = userId;
        this.routeId = routeId;
        this.notes = notes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
