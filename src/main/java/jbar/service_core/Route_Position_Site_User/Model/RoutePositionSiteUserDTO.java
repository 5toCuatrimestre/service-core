package jbar.service_core.Route_Position_Site_User.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "DTO for Route-Position-Site-User relationship")
public class RoutePositionSiteUserDTO {

    public interface Create {}
    public interface Update {}


    @Schema(description = "Route ID", example = "1")
    @NotNull(groups = {Create.class, Update.class}, message = "Route ID cannot be null.")
    private Integer routeId;

    @Schema(description = "PositionSite ID", example = "5")
    @NotNull(groups = {Create.class, Update.class}, message = "PositionSite ID cannot be null.")
    private Integer positionSiteId;

    @Schema(description = "User ID", example = "7")
    @NotNull(groups = {Create.class, Update.class}, message = "User ID cannot be null.")
    private Integer userId;

    @Schema(description = "Creation timestamp", example = "2024-02-26T10:00:00")
    @NotNull(groups = {Create.class}, message = "CreatedAt cannot be null.")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-02-26T12:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Deletion timestamp", example = "2024-02-27T08:00:00")
    private LocalDateTime deletedAt;

    // 🔹 Constructores
    public RoutePositionSiteUserDTO() {}

    public RoutePositionSiteUserDTO(Integer routeId, Integer positionSiteId, Integer userId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.routeId = routeId;
        this.positionSiteId = positionSiteId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // 🔹 Getters y Setters con validaciones
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
