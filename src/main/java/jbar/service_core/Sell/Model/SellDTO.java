package jbar.service_core.Sell.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Sell_Detail.Model.SellDetailDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SellDTO {

    @NotNull(message = "Error with user ID")
    private Integer userId;

    @NotNull(message = "Error with waiter ID")
    private Integer waiterId;

    @NotNull(message = "Error with total price")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @NotNull(message = "Error with sell date")
    private LocalDateTime sellDate;

    @NotNull(message = "Error with sell time")
    private LocalTime sellTime;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "Sell details are required")
    private List<SellDetailDTO> sellDetails;

    public SellDTO() {
    }

    public SellDTO(Integer userId, Integer waiterId, Double totalPrice, LocalDateTime sellDate, LocalTime sellTime, Boolean status, List<SellDetailDTO> sellDetails) {
        this.userId = userId;
        this.waiterId = waiterId;
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.sellTime = sellTime;
        this.status = status;
        this.sellDetails = sellDetails;
    }

    // Getters y Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDateTime sellDate) {
        this.sellDate = sellDate;
    }

    public LocalTime getSellTime() {
        return sellTime;
    }

    public void setSellTime(LocalTime sellTime) {
        this.sellTime = sellTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<SellDetailDTO> getSellDetails() {
        return sellDetails;
    }

    public void setSellDetails(List<SellDetailDTO> sellDetails) {
        this.sellDetails = sellDetails;
    }
}
