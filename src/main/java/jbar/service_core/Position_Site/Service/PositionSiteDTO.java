package jbar.service_core.Position_Site.Service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Site.Model.Site;

import java.sql.Date;

@Schema(description = "Position-Site Data Transfer Object (DTO)")
public class PositionSiteDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}

    @Schema(description = "Position ID", example = "1")
    @NotNull(groups = {Create.class}, message = "Position ID cannot be null.")
    private Integer positionId;

    @Schema(description = "Site ID", example = "1")
    @NotNull(groups = {Create.class}, message = "Site ID cannot be null.")
    private Integer siteId;

    @Schema(description = "Capacity of the position site", example = "50")
    @Positive(message = "Capacity must be greater than 0.")
    private Integer capacity;

    @Schema(description = "X Coordinate Location", example = "19.432608")
    private Double xLocation;

    @Schema(description = "Y Coordinate Location", example = "-99.133209")
    private Double yLocation;

    public PositionSiteDTO() {}

    public PositionSiteDTO(Integer positionId, Integer siteId, Integer capacity, Double xLocation, Double yLocation) {
        this.positionId = positionId;
        this.siteId = siteId;
        this.capacity = capacity;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getXLocation() {
        return xLocation;
    }

    public void setXLocation(Double xLocation) {
        this.xLocation = xLocation;
    }

    public Double getYLocation() {
        return yLocation;
    }

    public void setYLocation(Double yLocation) {
        this.yLocation = yLocation;
    }
}
