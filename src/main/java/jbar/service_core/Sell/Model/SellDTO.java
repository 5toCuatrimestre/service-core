package jbar.service_core.Sell.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Sell_Detail.Model.SellDetailDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SellDTO {
    @NotNull(message = "Error with total price")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @NotNull(message = "Error with sell date")
    private LocalDateTime sellDate;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "Sell details are required")
    private List<SellDetailDTO> sellDetails;

    public SellDTO() {
    }

    public SellDTO(Double totalPrice, LocalDateTime sellDate, Boolean status, List<SellDetailDTO> sellDetails) {
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.status = status;
        this.sellDetails = sellDetails;
    }

    // Getters y Setters

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
