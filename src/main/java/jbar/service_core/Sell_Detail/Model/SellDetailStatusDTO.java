package jbar.service_core.Sell_Detail.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SellDetailStatusDTO {
    @NotNull(message = "Sell Detail ID is required")
    private Integer sellDetailId;

    @NotNull(message = "Position Site ID is required")
    private Integer positionSiteId;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Waiter Name is required")
    private String nameWaiter;

    @NotNull(message = "Status is required")
    private SellDetailStatus.Status status;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    public SellDetailStatusDTO() {
    }

    public SellDetailStatusDTO(Integer sellDetailId, Integer positionSiteId, String name, String nameWaiter, SellDetailStatus.Status status, Integer quantity) {
        this.sellDetailId = sellDetailId;
        this.positionSiteId = positionSiteId;
        this.name = name;
        this.nameWaiter = nameWaiter;
        this.status = status;
        this.quantity = quantity;
    }

    public Integer getSellDetailId() {
        return sellDetailId;
    }

    public void setSellDetailId(Integer sellDetailId) {
        this.sellDetailId = sellDetailId;
    }

    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWaiter() {
        return nameWaiter;
    }

    public void setNameWaiter(String nameWaiter) {
        this.nameWaiter = nameWaiter;
    }

    public SellDetailStatus.Status getStatus() {
        return status;
    }

    public void setStatus(SellDetailStatus.Status status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 