package jbar.service_core.Site_Company.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for SiteCompany")
public class SiteCompanyDTO {

    public interface Create {}

    @Schema(description = "ID of the site.", example = "1")
    @NotNull(groups = {Create.class}, message = "Site ID cannot be null.")
    private Integer siteId;

    @Schema(description = "ID of the company.", example = "1")
    @NotNull(groups = {Create.class}, message = "Company ID cannot be null.")
    private Integer companyId;

    public SiteCompanyDTO() {
    }

    public SiteCompanyDTO(Integer siteId, Integer companyId) {
        this.siteId = siteId;
        this.companyId = companyId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
