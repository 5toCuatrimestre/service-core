package jbar.service_core.Company.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompanyDTO {
    @NotNull(message = "Error with company ID")
    private Integer companyId;

    @NotBlank(message = "Error with company name")
    private String name;

    @NotBlank(message = "Error with company address")
    private String address;

    private Boolean status;

    public CompanyDTO() {
    }

    public CompanyDTO(Integer companyId, String name, String address, Boolean status) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
