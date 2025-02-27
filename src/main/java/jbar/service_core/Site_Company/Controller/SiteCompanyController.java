package jbar.service_core.Site_Company.Controller;

import jbar.service_core.Site_Company.Model.SiteCompany;
import jbar.service_core.Site_Company.Model.SiteCompanyDTO;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/site-company")
public class SiteCompanyController {

    private final SiteCompanyService siteCompanyService;

    @Autowired
    public SiteCompanyController(SiteCompanyService siteCompanyService) {
        this.siteCompanyService = siteCompanyService;
    }

    /**
     * 🔹 Crear una nueva relación Site-Company
     */
    @PostMapping("/create")
    public ResponseEntity<Message> createSiteCompany(@RequestBody SiteCompanyDTO siteCompanyDTO) {
        return siteCompanyService.createSiteCompany(siteCompanyDTO);
    }

    /**
     * 🔹 Obtener todas las relaciones Site-Company de una empresa
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Message> getSitesByCompany(@PathVariable Integer companyId) {
        return siteCompanyService.getSitesByCompany(companyId);
    }

    /**
     * 🔹 Obtener todas las relaciones Site-Company de un sitio
     */
    @GetMapping("/site/{siteId}")
    public ResponseEntity<Message> getCompaniesBySite(@PathVariable Integer siteId) {
        return siteCompanyService.getCompaniesBySite(siteId);
    }

    /**
     * 🔹 Obtener una relación específica por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable Integer id) {
        return siteCompanyService.getById(id);
    }

    /**
     * 🔹 Eliminar una relación Site-Company
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteSiteCompany(@PathVariable Integer id) {
        return siteCompanyService.deleteSiteCompany(id);
    }
}
