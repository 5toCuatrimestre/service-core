package jbar.service_core.Site.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Date;

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
    @NotNull(groups = {Create.class})
    private Date createdAt;

    @Schema(description = "Timestamp when the site was last updated.", example = "2024-02-25")
    @NotNull(groups = {Update.class})
    private Date updatedAt;

    @Schema(description = "Timestamp when the site was deleted (if applicable).", example = "2024-02-25")
    private Date deletedAt;

    public SiteDTO() {}

    public SiteDTO(String name, String location, Boolean status, Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.location = location;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Site name cannot be blank.")
    @Size(min = 2, max = 100, message = "Site name must be between 2 and 100 characters.")
    String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "Site name cannot be blank.")
                        @Size(min = 2, max = 100, message = "Site name must be between 2 and 100 characters.")
                        String name) {
        this.name = name;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Site location cannot be blank.")
    @Size(max = 255, message = "Site location must not exceed 255 characters.")
    String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank(groups = {Create.class, Update.class}, message = "Site location cannot be blank.")
                            @Size(max = 255, message = "Site location must not exceed 255 characters.")
                            String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public @NotNull(groups = {Create.class})
    Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(groups = {Create.class}) Date createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(groups = {Update.class})
    Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(groups = {Update.class}) Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
