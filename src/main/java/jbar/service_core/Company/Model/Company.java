package jbar.service_core.Company.Model;

import jakarta.persistence.*;
import jbar.service_core.Site.Model.Site;

import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "address", columnDefinition = "TEXT", nullable = true)
    private String address;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Site> sites;

    public Company() {
    }

    public Company(Integer companyId, String name, String address, Boolean status) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.status = status;
    }

    // Getters y Setters

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
}
