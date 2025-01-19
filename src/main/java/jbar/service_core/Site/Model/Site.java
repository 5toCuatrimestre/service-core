package jbar.service_core.Site.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer siteId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "VARCHAR(255)", nullable = false)
    private String location;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    public Site() {
    }

    public Site(Integer siteId, String name, String location, Boolean status) {
        this.siteId = siteId;
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
