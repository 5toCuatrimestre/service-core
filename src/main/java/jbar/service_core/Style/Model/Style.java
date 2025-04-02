package jbar.service_core.Style.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jbar.service_core.Company_Style.Model.CompanyStyle;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "style")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer styleId;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 🔹 Se mapea JSON con nombres en mayúscula, pero las variables internas están en minúscula (buena práctica)
    @JsonProperty("H1")
    @Column(name = "h1", nullable = false)
    private String h1;

    @JsonProperty("H2")
    @Column(name = "h2", nullable = false)
    private String h2;

    @JsonProperty("H3")
    @Column(name = "h3", nullable = false)
    private String h3;

    @JsonProperty("P")
    @Column(name = "p", nullable = false)
    private String p;

    @JsonProperty("BgCard")
    @Column(name = "bg_card", nullable = false)
    private String bgCard;

    @JsonProperty("BgInterface")
    @Column(name = "bg_interface", nullable = false)
    private String bgInterface;

    @JsonProperty("BgButton")
    @Column(name = "bg_button", nullable = false)
    private String bgButton;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyStyle> companyStyles;

    public Style() {}

    public Style(Integer styleId, Boolean status, String h1, String h2, String h3, String p, String bgCard, String bgInterface, String bgButton) {
        this.styleId = styleId;
        this.status = status;
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.p = p;
        this.bgCard = bgCard;
        this.bgInterface = bgInterface;
        this.bgButton = bgButton;
    }

    // Getters y Setters
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
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

    public String getBgCard() {
        return bgCard;
    }

    public void setBgCard(String bgCard) {
        this.bgCard = bgCard;
    }

    public String getBgInterface() {
        return bgInterface;
    }

    public void setBgInterface(String bgInterface) {
        this.bgInterface = bgInterface;
    }

    public String getBgButton() {
        return bgButton;
    }

    public void setBgButton(String bgButton) {
        this.bgButton = bgButton;
    }

    public List<CompanyStyle> getCompanyStyles() {
        return companyStyles;
    }

    public void setCompanyStyles(List<CompanyStyle> companyStyles) {
        this.companyStyles = companyStyles;
    }
}
