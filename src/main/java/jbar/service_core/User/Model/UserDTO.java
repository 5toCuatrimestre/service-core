package jbar.service_core.User.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import java.sql.Date;

@Schema(description = "User DTO")
public class UserDTO {

    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
    //NO PUSE EL ID EN EL DTO PORQUE LO MANEJAMOS POR FUERA EN UN PATHVARIABLE
    @Schema(description = "First name of the user.", example = "John")
    @NotBlank(groups = {Create.class, Update.class}, message = "User name cannot be blank.")
    @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters.")
    private String name;

    @Schema(description = "Last name of the user.", example = "Doe")
    @NotBlank(groups = {Create.class, Update.class}, message = "User last name cannot be blank.")
    @Size(min = 2, max = 200, message = "User last name must be between 2 and 200 characters.")
    private String lastName;

    @Schema(description = "Email address of the user.", example = "john.doe@example.com")
    @NotBlank(groups = {Create.class, Update.class}, message = "User email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @Schema(description = "Password of the user (must be at least 8 characters).", example = "SecureP@ss123")
    @NotBlank(groups = {Create.class}, message = "User password cannot be blank.")
    @Size(min = 8, message = "Password must have at least 8 characters.")
    private String password;

    @Schema(description = "Current status of the user.", example = "ACTIVE")
    @NotNull(groups = {Update.class}, message = "User status cannot be null when updating or changing status.")
    private Status status;

    @Schema(description = "User role.", example = "ADMIN")
    @NotNull(groups = {Create.class, Update.class}, message = "User role cannot be null.")
    private String rol;

    @Schema(description = "Phone number of the user.", example = "+123456789")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format.")
    private String phoneNumber;

    @Schema(description = "Timestamp when the user was created.", example = "2024-02-11")
    @NotNull(groups = {Create.class})
    private Date createdAt;

    @Schema(description = "Timestamp when the user was last updated.", example = "2024-02-11")
    @NotNull(groups = {Update.class})
    private Date updatedAt;

    @Schema(description = "Timestamp when the user was deleted (if applicable).", example = "2024-02-11")
    private Date deletedAt;

    public UserDTO() {
    }

    public UserDTO(String name, String lastName, String email, String password, Status status, String rol, String phoneNumber, Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.rol = rol;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "User name cannot be blank.") @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters.") String getName() {
        return name;
    }

    public void setName(@NotBlank(groups = {Create.class, Update.class}, message = "User name cannot be blank.") @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters.") String name) {
        this.name = name;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "User last name cannot be blank.") @Size(min = 2, max = 200, message = "User last name must be between 2 and 200 characters.") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(groups = {Create.class, Update.class}, message = "User last name cannot be blank.") @Size(min = 2, max = 200, message = "User last name must be between 2 and 200 characters.") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(groups = {Create.class, Update.class}, message = "User email cannot be blank.") @Email(message = "Invalid email format.") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(groups = {Create.class, Update.class}, message = "User email cannot be blank.") @Email(message = "Invalid email format.") String email) {
        this.email = email;
    }

    public @NotNull(groups = {Update.class, ChangeStatus.class}, message = "User status cannot be null when updating or changing status.") Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull(groups = {Update.class, ChangeStatus.class}, message = "User status cannot be null when updating or changing status.") Status status) {
        this.status = status;
    }

    public Rol getRol() {
        return Rol.valueOf(rol);
    }

    public void setRol(@NotNull(groups = {Create.class, Update.class}, message = "User role cannot be null.") String rol) {
        this.rol = rol;
    }

    public @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format.") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format.") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotNull(groups = {Create.class}) Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(groups = {Create.class}) Date createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(groups = {Update.class}) Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(groups = {Update.class}) Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public @NotBlank(groups = {Create.class}, message = "User password cannot be blank.") @Size(min = 8, message = "Password must have at least 8 characters.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(groups = {Create.class}, message = "User password cannot be blank.") @Size(min = 8, message = "Password must have at least 8 characters.") String password) {
        this.password = password;
    }
}
