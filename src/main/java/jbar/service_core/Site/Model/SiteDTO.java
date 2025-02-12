package jbar.service_core.Site.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Site DTO")
public class SiteDTO {

    @NotBlank(message = "Site name cannot be blank.")
    private String name;

    @NotBlank(message = "Site location cannot be blank.")
    private String location;

    private Boolean status;

    public SiteDTO() {
    }

    public SiteDTO(String name, String location, Boolean status) {
        this.name = name;
        this.location = location;
        this.status = status;
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
