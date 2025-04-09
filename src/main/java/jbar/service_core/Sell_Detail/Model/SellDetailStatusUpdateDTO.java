package jbar.service_core.Sell_Detail.Model;

import jakarta.validation.constraints.NotNull;

public class SellDetailStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private SellDetailStatus.Status status;

    public SellDetailStatusUpdateDTO() {
    }

    public SellDetailStatusUpdateDTO(SellDetailStatus.Status status) {
        this.status = status;
    }

    public SellDetailStatus.Status getStatus() {
        return status;
    }

    public void setStatus(SellDetailStatus.Status status) {
        this.status = status;
    }
} 