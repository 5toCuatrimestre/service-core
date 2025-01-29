package jbar.service_core.Company_Style.Model;

import jakarta.persistence.*;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Style.Model.Style;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_style")
public class CompanyStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyStyleId;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public CompanyStyle() {
    }

    public CompanyStyle(Integer companyStyleId, Company company, Style style, Boolean status) {
        this.companyStyleId = companyStyleId;
        this.company = company;
        this.style = style;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getCompanyStyleId() {
        return companyStyleId;
    }

    public void setCompanyStyleId(Integer companyStyleId) {
        this.companyStyleId = companyStyleId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
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
}
