package jbar.service_core.Company_Style.Model;

import jakarta.persistence.*;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Style.Model.Style;

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

    public CompanyStyle() {
    }

    public CompanyStyle(Integer companyStyleId, Company company, Style style, Boolean status) {
        this.companyStyleId = companyStyleId;
        this.company = company;
        this.style = style;
        this.status = status;
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
}
