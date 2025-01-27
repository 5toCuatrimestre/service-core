package jbar.service_core.Product.Model;

import jakarta.persistence.*;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Company.Model.Company;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @ManyToMany
    @JoinTable(
            name = "product_company",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sell> sells;

    public Product() {
    }

    public Product(Integer productId, String name, String description, Double price, Boolean status) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    // Getters y Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Sell> getSells() {
        return sells;
    }

    public void setSells(List<Sell> sells) {
        this.sells = sells;
    }
}
