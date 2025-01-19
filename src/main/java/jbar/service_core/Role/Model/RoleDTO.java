package jbar.service_core.Role.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    @NotNull(message = "Error with role ID")
    private Integer roleId;

    @NotBlank(message = "Error with role name")
    private String name;

    private String description;

    private Boolean status;

    public RoleDTO() {
    }

    public RoleDTO(Integer roleId, String name, String description, Boolean status) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
}
