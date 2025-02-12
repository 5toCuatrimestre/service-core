package jbar.service_core.Position.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Position DTO")
public class PositionDTO {

    @NotBlank(message = "Position name cannot be blank.")
    private String name;

    private String description;
    private Boolean status;

    public PositionDTO() {
    }

    public PositionDTO(String name, String description, Boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
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
