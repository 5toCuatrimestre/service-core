package jbar.service_core.Multimedia.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Image DTO")
public class MultimediaDTO {
    public interface Find {
    }

    public interface Create {
    }

    public interface Update {
    }

    @Schema(description = "Unique identifier of the image.", example = "123")
    @NotNull(groups = {Find.class, Update.class}, message = "El ID de la imagen no puede ser nulo.")
    @Min(value = 1, message = "El ID debe ser mayor a 0.")
    private Integer id;

    @Schema(description = "URL of the image.", example = "https://example.com/image.jpg")
    @NotBlank(groups = {Create.class, Update.class}, message = "La URL de la imagen no puede estar vacía.")
    @Size(groups = {Create.class, Update.class}, max = 1000, message = "La URL de la imagen no puede tener más de 255 caracteres.")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
