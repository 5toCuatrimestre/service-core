package jbar.service_core.Menu.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "Menu DTO")
public class MenuDTO {

    @Schema(description = "Name of the menu.", example = "Main Menu")
    @NotBlank(message = "Menu name cannot be blank.")
    @Size(min = 2, max = 100, message = "Menu name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Description of the menu.", example = "The main menu of the restaurant.")
    @NotBlank(message = "Menu description cannot be blank.")
    @Size(min = 5, message = "Menu description must be at least 5 characters long.")
    private String description;

    @Schema(description = "Status of the menu.", example = "true")
    @NotNull(message = "Menu status cannot be null.")
    private Boolean status;

    @Schema(description = "Creation timestamp.", example = "2025-03-17T10:00:00")
    @NotNull(message = "Menu creation timestamp cannot be null.")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp.", example = "2025-03-17T10:00:00")
    private LocalDateTime updatedAt;

    public MenuDTO() {}

    public MenuDTO(String name, String description, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
}
