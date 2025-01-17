package jbar.service_core.User.Model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.constraints.*;
import jbar.service_core.Util.Enum.Status;

import java.sql.Date;
public class UserDTO {
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "Error with user id")
    private Integer userId;
    @NotBlank(groups = {Create.class, Update.class}, message = "Error with user name")
    private String name;
    @NotBlank(groups = {Create.class, Update.class}, message = "Error with user email")
    private String email;
    @NotBlank(groups = {Create.class, Update.class}, message = "Error with user password")
    private String password;
    @NotBlank(groups = {ChangeStatus.class}, message = "Error with user status")
    private Status status;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String name, String email, String password, Status status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public @NotNull(groups = {Update.class, ChangeStatus.class}, message = "Error with user id") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {Update.class, ChangeStatus.class}, message = "Error with user id") Integer userId) {
        this.userId = userId;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Error with user name") String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "Error with user name") String name) {
        this.name = name;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Error with user email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(groups = {Create.class, Update.class}, message = "Error with user email") String email) {
        this.email = email;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "Error with user password") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(groups = {Create.class, Update.class}, message = "Error with user password") String password) {
        this.password = password;
    }

    public @NotBlank(groups = {ChangeStatus.class}, message = "Error with user status") Status getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(groups = {ChangeStatus.class}, message = "Error with user status") Status status) {
        this.status = status;
    }
}
