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

    @Column(name = "header")
    private String header;

    @Column(name = "h1")
    private String h1;

    @Column(name = "h2")
    private String h2;

    @Column(name = "h3")
    private String h3;

    @Column(name = "p")
    private String p;

    @Column(name = "by_interface")
    private String byInterface;

    @Column(name = "by_button")
    private String byButton;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyStyle> companyStyles;

    public Style() {}

    public Style(Integer styleId, String name, String description, Boolean status) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
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

    public List<CompanyStyle> getCompanyStyles() {
        return companyStyles;
    }

    public void setCompanyStyles(List<CompanyStyle> companyStyles) {
        this.companyStyles = companyStyles;
    }
}
