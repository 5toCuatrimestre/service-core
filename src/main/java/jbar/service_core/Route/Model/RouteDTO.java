package jbar.service_core.Route.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class RouteDTO {
    private Integer routeId;

    @NotBlank(message = "Route name cannot be blank")
    @NotNull(message = "Error with role ID")
    private String name;

    private Double distance;
    private Integer estimatedTime;
    private String difficultyLevel;
    private String status;

    public RouteDTO() {
    }

    public RouteDTO(Integer routeId, String name, Double distance, Integer estimatedTime, String difficultyLevel, String status) {
        this.routeId = routeId;
        this.name = name;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.difficultyLevel = difficultyLevel;
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

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
