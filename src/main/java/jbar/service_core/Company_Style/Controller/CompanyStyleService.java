package jbar.service_core.Company_Style.Controller;

import jbar.service_core.Company.Model.Company;

import jbar.service_core.Company.Model.CompanyRepository;
import jbar.service_core.Company_Style.Model.CompanyStyle;
import jbar.service_core.Company_Style.Model.CompanyStyleDTO;

import jbar.service_core.Company_Style.Model.CompanyStyleRepository;
import jbar.service_core.Style.Model.Style;

import jbar.service_core.Style.Model.StyleRepository;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyStyleService {
    private final Logger log = LoggerFactory.getLogger(CompanyStyleService.class);

    private final CompanyStyleRepository companyStyleRepository;
    private final CompanyRepository companyRepository;
    private final StyleRepository styleRepository;

    @Autowired
    public CompanyStyleService(CompanyStyleRepository companyStyleRepository, CompanyRepository companyRepository, StyleRepository styleRepository) {
        this.companyStyleRepository = companyStyleRepository;
        this.companyRepository = companyRepository;
        this.styleRepository = styleRepository;
    }

    public ResponseEntity<Message> create(CompanyStyleDTO dto) {
        try {
            // Validar que la compañía exista
            Optional<Company> company = companyRepository.findById(dto.getCompanyId());
            if (company.isEmpty()) {
                log.warn("Company with id {} not found", dto.getCompanyId());
                return new ResponseEntity<>(new Message(null, "Company not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            // Validar que el estilo exista
            Optional<Style> style = styleRepository.findById(dto.getStyleId());
            if (style.isEmpty()) {
                log.warn("Style with id {} not found", dto.getStyleId());
                return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            // Crear la relación CompanyStyle
            CompanyStyle companyStyle = new CompanyStyle();
            companyStyle.setCompany(company.get());
            companyStyle.setStyle(style.get());
            companyStyle.setStatus(dto.getStatus());
            companyStyleRepository.save(companyStyle);

            log.info("CompanyStyle created successfully: {}", companyStyle);
            return new ResponseEntity<>(new Message(companyStyle, "CompanyStyle created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating CompanyStyle: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating CompanyStyle", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        try {
            Optional<CompanyStyle> companyStyle = companyStyleRepository.findById(id);
            if (companyStyle.isEmpty()) {
                log.warn("CompanyStyle with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "CompanyStyle not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            companyStyleRepository.delete(companyStyle.get());
            log.info("CompanyStyle with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "CompanyStyle deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting CompanyStyle: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error deleting CompanyStyle", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> findAll() {
        try {
            return new ResponseEntity<>(new Message(companyStyleRepository.findAll(), "CompanyStyles retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving CompanyStyles: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error retrieving CompanyStyles", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> findById(Integer id) {
        try {
            Optional<CompanyStyle> companyStyle = companyStyleRepository.findById(id);
            if (companyStyle.isEmpty()) {
                log.warn("CompanyStyle with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "CompanyStyle not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new Message(companyStyle.get(), "CompanyStyle found", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving CompanyStyle with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error retrieving CompanyStyle", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
