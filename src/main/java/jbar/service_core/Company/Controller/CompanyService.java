
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

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Company> companies = companyRepository.findAll();
        log.info("All companies retrieved successfully");
        return new ResponseEntity<>(new Message(companies, "Companies retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            log.info("Company with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(company.get(), "Company found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Company with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setStatus(companyDTO.getStatus());
        companyRepository.save(company);
        log.info("Company created successfully: {}", company);
        return new ResponseEntity<>(new Message(company, "Company created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, CompanyDTO companyDTO) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            company.setName(companyDTO.getName());
            company.setAddress(companyDTO.getAddress());
            company.setStatus(companyDTO.getStatus());
            companyRepository.save(company);
            log.info("Company with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(company, "Company updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Company with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            companyRepository.delete(company.get());
            log.info("Company with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Company deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Company with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
