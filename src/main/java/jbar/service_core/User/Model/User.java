package jbar.service_core.User.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "name", columnDefinition = "VARCHAR(100)",nullable = false)
    private String name;
    @Column(name = "lastName", columnDefinition = "VARCHAR(200)",nullable = false)
    private String lastName;
    @Column(name = "email", columnDefinition = "VARCHAR(100)",nullable = false)
    private String email;
    @Column(name = "password", columnDefinition = "VARCHAR(100)",nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Rol rol;
    @Column(name = "phone_number", columnDefinition = "VARCHAR(15)")
    private String phoneNumber;
    @Column(name="created_at")
    private Date createdAt;
    @JsonIgnore
    @Column(name="updated_at")
    private Date updatedAt;
    @JsonIgnore
    @Column(name="deleted_at")
    private Date deletedAt;
    @JsonIgnore
    @Column(name = "code", columnDefinition = "VARCHAR(255)")
    private String code;
    @JsonIgnore
    @Column(name = "code_generated_at")
    private Date codeGeneratedAt;

    public User() {
    }

    public User(Integer userId, String name, String lastName, String email, String password, Status status, Rol rol, String phoneNumber, Date createdAt, Date updatedAt, Date deletedAt, String code, Date codeGeneratedAt) {
        this.userId = userId;
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
        this.code = code;
        this.codeGeneratedAt = codeGeneratedAt;
    }

    public User(String name, String lastName, String email, String password, Status status, Rol rol, String phoneNumber, Date createdAt, Date updatedAt, Date deletedAt, String code, Date codeGeneratedAt) {
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
        this.code = code;
        this.codeGeneratedAt = codeGeneratedAt;
    }

    public User(String name, String lastName, String email, String password, Status status, Rol rol, String phoneNumber, Date createdAt) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.rol = rol;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCodeGeneratedAt() {
        return codeGeneratedAt;
    }

    public void setCodeGeneratedAt(Date codeGeneratedAt) {
        this.codeGeneratedAt = codeGeneratedAt;
    }
}
