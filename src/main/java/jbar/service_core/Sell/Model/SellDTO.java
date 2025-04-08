package jbar.service_core.Sell.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Sell_Detail.Model.SellDetailDTO;

import java.sql.Date;
import java.sql.Time;  // Usamos java.sql.Time para representar el tiempo
import java.sql.Timestamp;
import java.util.List;

@Schema(description = "Sell DTO")
public class SellDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @NotNull(message = "Sell date is required")
    private Timestamp sellDate;  // Usamos java.sql.Timestamp para la fecha y hora completas

    @NotNull(message = "Sell time is required")
    private Time sellTime;  // Usamos java.sql.Time para el tiempo // Usamos java.sql.Time para el tiempo

    @NotNull(message = "Position Site ID is required")
    private Integer position_site_id;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "Sell details are required")
    private List<SellDetailDTO> sellDetails;

    public SellDTO() {
    }

    public SellDTO(Integer userId, Double totalPrice, Timestamp sellDate, Time sellTime, Integer position_site_id, Boolean status, List<SellDetailDTO> sellDetails) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.sellDate = sellDate;
        this.sellTime = sellTime;
        this.position_site_id = position_site_id;
        this.status = status;
        this.sellDetails = sellDetails;
    }

    public @NotNull(message = "User ID is required") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User ID is required") Integer userId) {
        this.userId = userId;
    }

    public @NotNull(message = "Total price is required") @Positive(message = "Total price must be positive") Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NotNull(message = "Total price is required") @Positive(message = "Total price must be positive") Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getSellDate() {
        return sellDate;
    }

    public void setSellDate(Timestamp sellDate) {
        this.sellDate = sellDate;
    }

    public @NotNull(message = "Sell time is required") Time getSellTime() {
        return sellTime;
    }

    public void setSellTime(@NotNull(message = "Sell time is required") Time sellTime) {
        this.sellTime = sellTime;
    }

    public @NotNull(message = "Position Site ID is required") Integer getPosition_site_id() {
        return position_site_id;
    }

    public void setPosition_site_id(@NotNull(message = "Position Site ID is required") Integer position_site_id) {
        this.position_site_id = position_site_id;
    }

    public @NotNull(message = "Status is required") Boolean getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") Boolean status) {
        this.status = status;
    }

    public @NotNull(message = "Sell details are required") List<SellDetailDTO> getSellDetails() {
        return sellDetails;
    }

    public void setSellDetails(@NotNull(message = "Sell details are required") List<SellDetailDTO> sellDetails) {
        this.sellDetails = sellDetails;
    }
}
