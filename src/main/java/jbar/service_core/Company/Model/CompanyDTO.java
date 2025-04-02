package jbar.service_core.Company.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Company Data Transfer Object (DTO)")
public class CompanyDTO {

    public interface Create {}
    public interface Update {}
    public interface UploadProfile {}


    private Integer companyId;

    @Schema(description = "Company name", example = "Tech Solutions Inc.")
    @NotBlank(groups = {Create.class, Update.class}, message = "Company name cannot be blank.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Company address", example = "1234 Tech Park, Silicon Valley, CA")
    @NotBlank(groups = {Create.class, Update.class}, message = "Company address cannot be blank.")
    @Size(max = 255, message = "Company address must not exceed 255 characters.")
    private String address;

    @Schema(description = "Url", example = "aaa")
    @NotBlank(groups = {Create.class, UploadProfile.class}, message = "url not blank")
    private String url;


    public CompanyDTO() {}

    public CompanyDTO(String name, String address) {
        this.name = name;
        this.address = address;
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
}
