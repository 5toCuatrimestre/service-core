package jbar.service_core.Site_Company.Controller;

import jbar.service_core.Site_Company.Model.SiteCompany;
import jbar.service_core.Site_Company.Model.SiteCompanyDTO;
import jbar.service_core.Site_Company.Model.SiteCompanyRepository;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SiteCompanyService {

    private final SiteCompanyRepository siteCompanyRepository;

    @Autowired
    public SiteCompanyService(SiteCompanyRepository siteCompanyRepository) {
        this.siteCompanyRepository = siteCompanyRepository;
    }

    /**
     * 🔹 Crear una relación Site-Company
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> createSiteCompany(SiteCompanyDTO dto) {
        if (dto.getSiteId() == null || dto.getCompanyId() == null) {
            return new ResponseEntity<>(new Message(null, "Site ID and Company ID are required.", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Site site = new Site();
        site.setSiteId(dto.getSiteId());

        Company company = new Company();
        company.setCompanyId(dto.getCompanyId());

        SiteCompany siteCompany = new SiteCompany(site, company);
        siteCompanyRepository.saveAndFlush(siteCompany);

        return new ResponseEntity<>(new Message(siteCompany, "SiteCompany created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    /**
     * 🔹 Obtener todos los sitios de una empresa
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getSitesByCompany(Integer companyId) {
        if (companyId == null) {
            return new ResponseEntity<>(new Message(null, "Company ID is required.", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Company company = new Company();
        company.setCompanyId(companyId);

        List<SiteCompany> sites = siteCompanyRepository.findByCompany(company);
        return new ResponseEntity<>(new Message(sites, "Sites retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    /**
     * 🔹 Obtener todas las empresas asociadas a un sitio
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getCompaniesBySite(Integer siteId) {
        if (siteId == null) {
            return new ResponseEntity<>(new Message(null, "Site ID is required.", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Site site = new Site();
        site.setSiteId(siteId);

        List<SiteCompany> companies = siteCompanyRepository.findBySite(site);
        return new ResponseEntity<>(new Message(companies, "Companies retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    /**
     * 🔹 Obtener una relación Site-Company por ID
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getById(Integer id) {
        if (id == null) {
            return new ResponseEntity<>(new Message(null, "ID is required.", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<SiteCompany> siteCompany = siteCompanyRepository.findById(id);
        return siteCompany.map(value ->
                        new ResponseEntity<>(new Message(value, "SiteCompany found", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(new Message(null, "SiteCompany not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    /**
     * 🔹 Eliminar una relación Site-Company (Soft Delete)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> deleteSiteCompany(Integer id) {
        if (id == null) {
            return new ResponseEntity<>(new Message(null, "ID is required.", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<SiteCompany> siteCompanyOptional = siteCompanyRepository.findById(id);
        if (siteCompanyOptional.isPresent()) {
            siteCompanyRepository.delete(siteCompanyOptional.get());
            return new ResponseEntity<>(new Message(null, "SiteCompany deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "SiteCompany not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
