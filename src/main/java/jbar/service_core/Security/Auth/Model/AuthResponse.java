package jbar.service_core.Security.Auth.Model;

public class AuthResponse {
    private String jwt;
    private Integer userId;
    private String email;
    private long expiration;

    public AuthResponse(String jwt, Integer userId, String email, long expiration) { // Cambiado aquí también
        this.jwt = jwt;
        this.userId = userId;
        this.email = email;
        this.expiration = expiration;
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
