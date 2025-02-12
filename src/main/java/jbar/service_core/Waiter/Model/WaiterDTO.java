package jbar.service_core.Waiter.Model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WaiterDTO {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Hire date is required")
    private LocalDateTime hireDate;

    private Boolean status;

    public WaiterDTO() {}

    public WaiterDTO(Integer userId, LocalDateTime hireDate, Boolean status) {
        this.userId = userId;
        this.hireDate = hireDate;
        this.status = status;
    }

    // Getters y Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
