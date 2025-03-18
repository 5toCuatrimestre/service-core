package jbar.service_core.Mutimedia.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jbar.service_core.User.Model.UserDTO;


@Schema(description = "Image DTO for user-related images")
public class MutimediaDTO {

    public interface Create {}
    public interface Update {}

    @Schema(description = "ID of the image", example = "1")
    @NotNull(groups = {Update.class}, message = "Image ID cannot be null when updating.")
    private Integer id;

    @Schema(description = "URL of the image", example = "https://example.com/path/to/image.jpg")
    @NotBlank(groups = {Create.class, Update.class}, message = "Image URL cannot be blank.")
    @Size(max = 500, message = "Image URL must be less than 500 characters.")
    private String url;

    public MutimediaDTO() {
    }

    public MutimediaDTO(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class}, message = "User name cannot be blank.") @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters.") String getName() {
        return url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}