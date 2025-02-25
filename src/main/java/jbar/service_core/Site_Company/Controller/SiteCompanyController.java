package jbar.service_core.Site_Company.Controller;

import jbar.service_core.Site_Company.Model.SiteCompany;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Company.Model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/site-company")
public class SiteCompanyController {

    @Autowired
    private SiteCompanyService siteCompanyService;

    @PostMapping("/create")
    public SiteCompany createSiteCompany(@RequestParam Integer siteId, @RequestParam Integer companyId) {
        Site site = new Site();
        site.setSiteId(siteId);

        Company company = new Company();
        company.setCompanyId(companyId);

        return siteCompanyService.createSiteCompany(site, company);
    }

    @GetMapping("/company/{companyId}")
    public List<SiteCompany> getSitesByCompany(@PathVariable Integer companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        return siteCompanyService.getSitesByCompany(company);
    }

    @GetMapping("/site/{siteId}")
    public List<SiteCompany> getCompaniesBySite(@PathVariable Integer siteId) {
        Site site = new Site();
        site.setSiteId(siteId);
        return siteCompanyService.getCompaniesBySite(site);
    }

    @GetMapping("/{id}")
    public Optional<SiteCompany> getById(@PathVariable Integer id) {
        return siteCompanyService.getById(id);
    }
}
