package jbar.service_core.Category.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jbar.service_core.Util.Enum.Status;
import java.sql.Date;
import java.time.LocalDateTime;

@Schema(description = "Category DTO")
public class CategoryDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    private Integer categoryId;

    @Schema(description = "Name of the category.", example = "Electronics")
    @NotBlank(groups = {Create.class, Update.class}, message = "Category name cannot be blank.")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Current status of the category.", example = "ACTIVE")
    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "Category status cannot be null when updating or changing status.")
    private Status status;

    @Schema(description = "Timestamp when the category was created.", example = "2024-02-11")
    @NotNull(groups = {Create.class})
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the category was last updated.", example = "2024-02-11")
    @NotNull(groups = {Update.class})
    private LocalDateTime updatedAt;

    @Schema(description = "Timestamp when the category was deleted (if applicable).", example = "2024-02-11")
    private LocalDateTime deletedAt;

    public CategoryDTO() {
    }

    public CategoryDTO(String name, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public @NotNull(groups = {Create.class}) LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(groups = {Create.class}) LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(groups = {Update.class}) LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(groups = {Update.class}) LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}