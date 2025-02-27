package jbar.service_core.Company.Controller;

import jbar.service_core.Company.Model.CompanyDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies") // REST recomendado en plural
@CrossOrigin(origins = "*") // Configurar según tu frontend
@Validated
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<Message> getAllCompanies() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getCompanyById(@PathVariable Integer id) {
        return companyService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createCompany(@RequestBody @Validated(CompanyDTO.Create.class) CompanyDTO companyDTO) {
        return companyService.create(companyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCompany(@PathVariable Integer id, @RequestBody @Validated(CompanyDTO.Update.class) CompanyDTO companyDTO) {
        return companyService.update(id, companyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteCompany(@PathVariable Integer id) {
        return companyService.delete(id);
    }
}
