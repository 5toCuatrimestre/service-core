package jbar.service_core.Site_Company.Model;

import jakarta.persistence.*;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Company.Model.Company;

@Entity
@Table(name = "site_company")
public class SiteCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public SiteCompany() {
    }

    public SiteCompany(Site site, Company company) {
        this.site = site;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
