package jbar.service_core.User.Model;

import jbar.service_core.Util.Enum.Status;
import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "name", columnDefinition = "VARCHAR(100)",nullable = false)
    private String name;
    @Column(name = "email", columnDefinition = "VARCHAR(100)",nullable = false)
    private String email;
    @Column(name = "password", columnDefinition = "VARCHAR(100)",nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    public User() {
    }

    public User(Integer userId, String name, String email, String password, Status status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
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
}
