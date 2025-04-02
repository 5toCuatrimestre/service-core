package jbar.service_core.Site.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Site Data Transfer Object (DTO)")
public class SiteDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    @Schema(description = "Site name", example = "Central Park Branch")
    @NotBlank(groups = {Create.class, Update.class}, message = "Site name cannot be blank.")
    @Size(min = 2, max = 100, message = "Site name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Site location", example = "123 Main Street, New York, NY")
    @NotBlank(groups = {Create.class, Update.class}, message = "Site location cannot be blank.")
    @Size(max = 255, message = "Site location must not exceed 255 characters.")
    private String location;

    @Schema(description = "Site status", example = "true")
    private Boolean status;

    @Schema(description = "Timestamp when the site was created.", example = "2024-02-25")
    private LocalDate createdAt;

    @Schema(description = "Timestamp when the site was last updated.", example = "2024-02-25")
    private LocalDate updatedAt;

    @Schema(description = "Timestamp when the site was deleted (if applicable).", example = "2024-02-25")
    private LocalDate deletedAt;

    public SiteDTO() {}

    public SiteDTO(String name, String location, Boolean status, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.name = name;
        this.location = location;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }
}
