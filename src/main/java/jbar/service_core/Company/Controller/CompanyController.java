package jbar.service_core.Company.Controller;

import jbar.service_core.Company.Model.CompanyDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")

public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * 🔹 Obtener todas las compañías
     */
    @GetMapping
    public ResponseEntity<Message> getAllCompanies() {
        return companyService.findAll();
    }

    /**
     * 🔹 Obtener una compañía por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getCompanyById(@PathVariable Integer id) {
        return companyService.findById(id);
    }

    /**
     * 🔹 Crear la empresa por defecto (ID = 1)
     * ❗ Solo se permite una empresa. Si ya existe, retorna 409 Conflict.
     */
    @PostMapping
    public ResponseEntity<Message> createCompany(@RequestBody @Validated(CompanyDTO.Create.class) CompanyDTO companyDTO) {
        return companyService.create(companyDTO);
    }

    /**
     * 🔹 Actualizar información de la empresa
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCompany(@PathVariable Integer id, @RequestBody @Validated(CompanyDTO.Update.class) CompanyDTO companyDTO) {
        return companyService.update(id, companyDTO);
    }

    /**
     * 🔹 Eliminar (Soft Delete) la empresa
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteCompany(@PathVariable Integer id) {
        return companyService.delete(id);
    }
}
