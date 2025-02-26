package jbar.service_core.Site_Company.Model;

import jbar.service_core.Site_Company.Model.SiteCompany;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Company.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteCompanyRepository extends JpaRepository<SiteCompany, Integer> {
    List<SiteCompany> findBySite(Site site);
    List<SiteCompany> findByCompany(Company company);
}
