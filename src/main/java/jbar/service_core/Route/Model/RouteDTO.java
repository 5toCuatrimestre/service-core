package jbar.service_core.Route.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Route DTO")
public class RouteDTO {

    @NotBlank(message = "Route name cannot be blank.")
    private String name;

    private Double distance;
    private Integer estimatedTime;
    private String difficultyLevel;
    private String status;

    public RouteDTO() {
    }

    public RouteDTO(String name, Double distance, Integer estimatedTime, String difficultyLevel, String status) {
        this.name = name;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
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
