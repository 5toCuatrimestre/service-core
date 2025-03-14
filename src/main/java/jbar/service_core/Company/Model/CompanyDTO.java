package jbar.service_core.Company.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Company Data Transfer Object (DTO)")
public class CompanyDTO {
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    @Schema(description = "Company name", example = "Tech Solutions Inc.")
    @NotBlank(groups = {Create.class, Update.class}, message = "Company name cannot be blank.")
    private String name;

    @Schema(description = "Company address", example = "1234 Tech Park, Silicon Valley, CA")
    @NotBlank(groups = {Create.class, Update.class}, message = "Company address cannot be blank.")
    private String address;

    @Schema(description = "Company creation timestamp", example = "2024-02-11T10:30:00")
    @NotNull(groups = {Create.class})
    private LocalDateTime createdAt;

    @Schema(description = "Company last updated timestamp", example = "2024-02-11T12:00:00")
    @NotNull(groups = {Update.class})
    private LocalDateTime updatedAt;

    @Schema(description = "Company deletion timestamp", example = "2024-02-11T15:00:00")
    private LocalDateTime deletedAt;

    public CompanyDTO() {}

    public CompanyDTO(String name, String address, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.name = name;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Company name cannot be blank.") String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "Company name cannot be blank.") String name) {
        this.name = name;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Company address cannot be blank.") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(groups = {Create.class, Update.class}, message = "Company address cannot be blank.") String address) {
        this.address = address;
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
