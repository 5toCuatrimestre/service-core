package jbar.service_core.Position.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PositionDTO {
    @NotNull(message = "Error with position ID")
    private Integer positionId;

    @NotBlank(message = "Error with position name")
    private String name;

    private String description;

    private Boolean status;

    public PositionDTO() {
    }

    public PositionDTO(Integer positionId, String name, String description, Boolean status) {
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
}
