package jbar.service_core.Company.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Company DTO")
public class CompanyDTO {

    @NotBlank(message = "Company name cannot be blank.")
    private String name;

    @NotBlank(message = "Company address cannot be blank.")
    private String address;

    private Boolean status;

    public CompanyDTO() {
    }

    public CompanyDTO(String name, String address, Boolean status) {
        this.name = name;
        this.address = address;
        this.status = status;
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
