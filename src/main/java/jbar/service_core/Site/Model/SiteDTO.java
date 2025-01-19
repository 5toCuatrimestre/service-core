package jbar.service_core.Site.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SiteDTO {
    @NotNull(message = "Error with site ID")
    private Integer siteId;

    @NotBlank(message = "Error with site name")
    private String name;

    @NotBlank(message = "Error with site location")
    private String location;

    private Boolean status;

    public SiteDTO() {
    }

    public SiteDTO(Integer siteId, String name, String location, Boolean status) {
        this.siteId = siteId;
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
