package jbar.service_core.Category.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jbar.service_core.Util.Enum.Status;
import java.sql.Date;

@Schema(description = "Category DTO")
public class CategoryDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    @Schema(description = "Name of the category.", example = "Electronics")
    @NotBlank(groups = {Create.class, Update.class}, message = "Category name cannot be blank.")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Current status of the category.", example = "ACTIVE")
    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "Category status cannot be null when updating or changing status.")
    private Status status;

    @Schema(description = "Timestamp when the category was created.", example = "2024-02-11")
    @NotNull(groups = {Create.class})
    private Date createdAt;

    @Schema(description = "Timestamp when the category was last updated.", example = "2024-02-11")
    @NotNull(groups = {Update.class})
    private Date updatedAt;

    @Schema(description = "Timestamp when the category was deleted (if applicable).", example = "2024-02-11")
    private Date deletedAt;

    public CategoryDTO() {
    }

    public CategoryDTO(String name, Status status, Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Category name cannot be blank.") @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters.") String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "Category name cannot be blank.") @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters.") String name) {
        this.name = name;
    }

    public @NotNull(groups = {Update.class, ChangeStatus.class}, message = "Category status cannot be null when updating or changing status.") Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull(groups = {Update.class, ChangeStatus.class}, message = "Category status cannot be null when updating or changing status.") Status status) {
        this.status = status;
    }

    public @NotNull(groups = {Create.class}) Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(groups = {Create.class}) Date createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(groups = {Update.class}) Date getUpdatedAt() {
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