package jbar.service_core.Position_Site.Service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PositionSiteDTO {
    @NotNull(message = "Error with position ID")
    private Integer positionId;

    @NotNull(message = "Error with site ID")
    private Integer siteId;

    @Positive(message = "Error with capacity")
    private Integer capacity;

    public PositionSiteDTO() {
    }

    public PositionSiteDTO(Integer positionId, Integer siteId, Integer capacity) {
        this.positionId = positionId;
        this.siteId = siteId;
        this.capacity = capacity;
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
}
