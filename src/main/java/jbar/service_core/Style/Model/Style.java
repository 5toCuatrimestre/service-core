package jbar.service_core.Style.Model;

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

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "h1", nullable = false)
    private String H1;

    @Column(name = "h2", nullable = false)
    private String H2;

    @Column(name = "h3", nullable = false)
    private String H3;

    @Column(name = "p", nullable = false)
    private String P;

    @Column(name = "bg_card", nullable = false)
    private String BgCard;

    @Column(name = "bg_interface", nullable = false)
    private String BgInterface;

    @Column(name = "bg_button", nullable = false)
    private String BgButton;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyStyle> companyStyles;

    public Style() {}

    public Style(Integer styleId, String name, String description, Boolean status,
                 String H1, String H2, String H3, String P, String BgCard, String BgInterface, String BgButton) {
        this.styleId = styleId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.H1 = H1;
        this.H2 = H2;
        this.H3 = H3;
        this.P = P;
        this.BgCard = BgCard;
        this.BgInterface = BgInterface;
        this.BgButton = BgButton;
    }

    // Getters y Setters
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
        return H1;
    }

    public void setH1(String h1) {
        H1 = h1;
    }

    public String getH2() {
        return H2;
    }

    public void setH2(String h2) {
        H2 = h2;
    }

    public String getH3() {
        return H3;
    }

    public void setH3(String h3) {
        H3 = h3;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getBgCard() {
        return BgCard;
    }

    public void setBgCard(String bgCard) {
        BgCard = bgCard;
    }

    public String getBgInterface() {
        return BgInterface;
    }

    public void setBgInterface(String bgInterface) {
        BgInterface = bgInterface;
    }

    public String getBgButton() {
        return BgButton;
    }

    public void setBgButton(String bgButton) {
        BgButton = bgButton;
    }

    public List<CompanyStyle> getCompanyStyles() {
        return companyStyles;
    }

    public void setCompanyStyles(List<CompanyStyle> companyStyles) {
        this.companyStyles = companyStyles;
    }
}
