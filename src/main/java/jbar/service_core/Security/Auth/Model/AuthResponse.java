package jbar.service_core.Security.Auth.Model;

import jbar.service_core.Util.Enum.Rol;

public class AuthResponse {
    private String jwt;
    private Integer userId;
    private Rol rol;
    private String email;
    private long expiration;

    public AuthResponse(String jwt, Integer userId, Rol rol, String email, long expiration) {
        this.jwt = jwt;
        this.userId = userId;
        this.rol = rol;  // Asegurar que se asigna correctamente
        this.email = email;
        this.expiration = expiration;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
