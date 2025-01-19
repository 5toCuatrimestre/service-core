package jbar.service_core.Company.Model;
import jakarta.persistence.*;



@Entity
@Table(name = "company")

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;


    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "address", columnDefinition = "VARCHAR(255)", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    public Company() {
    }

    public Company(Integer companyId, String name, String address, Boolean status) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.status = status;
    }

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

}
