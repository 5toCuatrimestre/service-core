package jbar.service_core.Style.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(description = "Style DTO")
public class StyleDTO {

    private Integer styleId;

    @NotBlank(message = "Style name cannot be blank")
    private String name;

    private String description;

    private Boolean status;

    @Schema(description = "Header color", example = "#FFFFFF")
    private String header;

    @Schema(description = "H1 color", example = "#000000")
    private String h1;

    @Schema(description = "H2 color", example = "#222222")
    private String h2;

    @Schema(description = "H3 color", example = "#444444")
    private String h3;

    @Schema(description = "Paragraph text color", example = "#666666")
    private String p;

    @Schema(description = "Background color for interface", example = "#EEEEEE")
    private String byInterface;

    @Schema(description = "Background color for buttons", example = "#FF0000")
    private String byButton;

    public StyleDTO() {}

    public StyleDTO(Integer styleId, String name, String description, Boolean status,
                    String header, String h1, String h2, String h3, String p,
                    String byInterface, String byButton) {
        this.styleId = styleId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.header = header;
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.p = p;
        this.byInterface = byInterface;
        this.byButton = byButton;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getByInterface() {
        return byInterface;
    }

    public void setByInterface(String byInterface) {
        this.byInterface = byInterface;
    }

    public String getByButton() {
        return byButton;
    }

    public void setByButton(String byButton) {
        this.byButton = byButton;
    }
}
