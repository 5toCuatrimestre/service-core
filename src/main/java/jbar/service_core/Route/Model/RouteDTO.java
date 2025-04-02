package jbar.service_core.Route.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jbar.service_core.Util.Enum.Status;

@Schema(description = "Route DTO")
public class RouteDTO {

    @NotBlank(message = "Route name cannot be blank.")
    private String name;

    @NotNull(message = "Route status cannot be null.")
    private Status status;

    public RouteDTO() {}

    public RouteDTO(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
