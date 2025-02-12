package jbar.service_core.Style.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Style DTO")
public class StyleDTO {

    private Integer styleId;

    @NotBlank(message = "Style name cannot be blank")
    private String name;

    private String description;

    private Boolean status;

    public StyleDTO() {
    }

    public StyleDTO(Integer styleId, String name, String description, Boolean status) {
        this.styleId = styleId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
