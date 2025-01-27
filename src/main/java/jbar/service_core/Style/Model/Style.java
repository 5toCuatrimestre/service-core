package jbar.service_core.Style.Model;

import jakarta.persistence.*;
import jbar.service_core.Company_Style.Model.CompanyStyle;

import java.util.List;

@Entity
@Table(name = "style")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer styleId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "style")
    private List<CompanyStyle> companyStyles;

    public Style() {
    }

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

    public List<CompanyStyle> getCompanyStyles() {
        return companyStyles;
    }

    public void setCompanyStyles(List<CompanyStyle> companyStyles) {
        this.companyStyles = companyStyles;
    }
}
