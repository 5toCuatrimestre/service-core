package jbar.service_core.Position.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Position Data Transfer Object (DTO)")
public class PositionDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    @Schema(description = "Position name", example = "Waiter Leader")
    @NotBlank(groups = {Create.class, Update.class}, message = "Position name cannot be blank.")
    @Size(min = 2, max = 100, message = "Position name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Position description", example = "Responsible for assigning tables to waiters.")
    @Size(max = 500, message = "Description must not exceed 500 characters.")
    private String description;

    @Schema(description = "Timestamp when the position was created.", example = "2024-02-25T10:30:00")
    @NotNull(groups = {Create.class})
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the position was last updated.", example = "2024-02-25T10:30:00")
    @NotNull(groups = {Update.class})
    private LocalDateTime updatedAt;

    @Schema(description = "Timestamp when the position was deleted (if applicable).", example = "2024-02-25T10:30:00")
    private LocalDateTime deletedAt;

    // 🔹 Constructor vacío
    public PositionDTO() {}

    // 🔹 Constructor con parámetros
    public PositionDTO(String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // 🔹 Getters y Setters con validaciones
    public @NotBlank(groups = {Create.class, Update.class}, message = "Position name cannot be blank.")
    @Size(min = 2, max = 100, message = "Position name must be between 2 and 100 characters.")
    String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "Position name cannot be blank.")
                        @Size(min = 2, max = 100, message = "Position name must be between 2 and 100 characters.")
                        String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public @Size(max = 500, message = "Description must not exceed 500 characters.")
    String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description must not exceed 500 characters.")
                               String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public @NotNull(groups = {Create.class}, message = "Created date cannot be null.")
    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(groups = {Create.class}, message = "Created date cannot be null.") LocalDateTime createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        }
    }

    public @NotNull(groups = {Update.class}, message = "Updated date cannot be null.")
    LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(groups = {Update.class}, message = "Updated date cannot be null.") LocalDateTime updatedAt) {
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
}
