package jbar.service_core.Style.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Style DTO")
public class StyleDTO {

    private Integer styleId;

    private Boolean status;

    @Schema(description = "Header color", example = "#FFFFFF")
    private String header;

    @Schema(description = "H1 color", example = "#000000")
    private String H1;

    @Schema(description = "H2 color", example = "#222222")
    private String H2;

    @Schema(description = "H3 color", example = "#444444")
    private String H3;

    @Schema(description = "Paragraph text color", example = "#666666")
    private String P;
    @Schema(description = "Background color for card", example = "#FFFFFF") // 🔹 AGREGADO
    private String bgCard;

    @Schema(description = "Background color for interface", example = "#EEEEEE")
    private String bgInterface;

    @Schema(description = "Background color for buttons", example = "#FF0000")
    private String bgButton;

    public StyleDTO() {}

    public StyleDTO(Integer styleId, Boolean status,
                    String header, String h1, String h2, String h3, String p,String bgCard,
                    String bgInterface, String bgButton) {
        this.styleId = styleId;


        this.status = status;
        this.header = header;
        this.H1 = h1;
        this.H2 = h2;
        this.H3 = h3;
        this.P = p;
        this.bgCard = bgCard; // 🔹 AGREGADO
        this.bgInterface = bgInterface;
        this.bgButton = bgButton;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
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
        return H1;
    }

    public void setH1(String h1) {
        this.H1 = h1;
    }

    public String getH2() {
        return H2;
    }

    public void setH2(String h2) {
        this.H2 = h2;
    }

    public String getH3() {
        return H3;
    }

    public void setH3(String h3) {
        this.H3 = h3;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        this.P = p;
    }

    public String getBgButton() {
        return bgButton;
    }

    public void setBgButton(String bgButton) {
        this.bgButton = bgButton;
    }

    public String getBgInterface() {
        return bgInterface;
    }

    public void setBgInterface(String bgInterface) {
        this.bgInterface = bgInterface;
    }

    public String getBgCard() {
        return bgCard;
    }

    public void setBgCard(String bgCard) {
        this.bgCard = bgCard;
    }
}
