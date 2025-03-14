package jbar.service_core.Company.Controller;

import jbar.service_core.Company.Model.Company;
import jbar.service_core.Company.Model.CompanyDTO;
import jbar.service_core.Company.Model.CompanyRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Company> companies = companyRepository.findAll();
        log.info("All companies retrieved successfully");
        return new ResponseEntity<>(new Message(companies, "Companies retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(value -> {
            log.info("Company with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Company found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Company with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(CompanyDTO companyDTO) {
        // ✅ Siempre se asigna manualmente el ID de la compañía por defecto (ID = 1)
        Optional<Company> existingCompany = companyRepository.findById(1);
        if (existingCompany.isPresent()) {
            log.warn("Default Company with ID 1 already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(null, "Default Company ID 1 already exists", TypesResponse.ERROR));
        }

        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setCreatedAt(LocalDateTime.now());

        companyRepository.save(company);

        log.info("Company created successfully: {}", company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(company, "Company created successfully", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, CompanyDTO companyDTO) {
        Optional<Company> existingCompanyOptional = companyRepository.findById(id);
        if (existingCompanyOptional.isPresent()) {
            Company company = existingCompanyOptional.get();
            if (companyDTO.getName() != null) company.setName(companyDTO.getName());
            if (companyDTO.getAddress() != null) company.setAddress(companyDTO.getAddress());
            company.setUpdatedAt(LocalDateTime.now());

            companyRepository.saveAndFlush(company);
            return ResponseEntity.ok(new Message(company, "Company updated", TypesResponse.SUCCESS));
        }
        return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setDeletedAt(LocalDateTime.now()); // Soft delete
            companyRepository.save(company);

            log.info("Company with id {} soft deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Company deleted (soft delete)", TypesResponse.SUCCESS), HttpStatus.OK);
        }

        log.warn("Company with id {} not found for deletion", id);
        return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

}
