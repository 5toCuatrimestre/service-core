package jbar.service_core.Site_Company.Controller;

import jbar.service_core.Site_Company.Model.SiteCompany;
import jbar.service_core.Site_Company.Model.SiteCompanyDTO;
import jbar.service_core.Site_Company.Model.SiteCompanyRepository;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Company.Model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SiteCompanyService {

    @Autowired
    private SiteCompanyRepository siteCompanyRepository;

    public SiteCompany createSiteCompany(SiteCompanyDTO dto) {
        Site site = new Site();
        site.setSiteId(dto.getSiteId());

        Company company = new Company();
        company.setCompanyId(dto.getCompanyId());

        SiteCompany siteCompany = new SiteCompany(site, company);
        return siteCompanyRepository.saveAndFlush(siteCompany);
    }


    public List<SiteCompany> getCompaniesBySite(Site site) {
        return siteCompanyRepository.findBySite(site);
    }

    public Optional<SiteCompany> getById(Integer id) {
        return siteCompanyRepository.findById(id);
    }
}
