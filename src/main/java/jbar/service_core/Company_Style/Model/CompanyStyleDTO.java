package jbar.service_core.Company_Style.Model;

import jakarta.validation.constraints.NotNull;

public class CompanyStyleDTO {
    @NotNull(message = "Company ID is required")
    private Integer companyId;

    @NotNull(message = "Style ID is required")
    private Integer styleId;

    @NotNull(message = "Status is required")
    private Boolean status;

    public CompanyStyleDTO() {
    }

    public CompanyStyleDTO(Integer companyId, Integer styleId, Boolean status) {
        this.companyId = companyId;
        this.styleId = styleId;
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
}
