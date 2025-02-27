package jbar.service_core.Site.Controller;

import jbar.service_core.Site.Model.Site;
import jbar.service_core.Site.Model.SiteDTO;
import jbar.service_core.Site.Model.SiteRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SiteService {
    private final Logger log = LoggerFactory.getLogger(SiteService.class);
    private final SiteRepository siteRepository;

    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * 🔹 Obtener todos los sitios
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Site> sites = siteRepository.findByDeletedAtIsNull();
        log.info("All active sites retrieved successfully.");
        return new ResponseEntity<>(new Message(sites, "Active sites retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    /**
     * 🔹 Obtener un sitio por ID
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Site> site = siteRepository.findById(id);
        return site.map(value -> {
            log.info("Site with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Site found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Site with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Site not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    /**
     * 🔹 Crear un nuevo sitio
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(SiteDTO siteDTO) {
        Site site = new Site();
        site.setName(siteDTO.getName());
        site.setLocation(siteDTO.getLocation());
        site.setStatus(siteDTO.getStatus() != null ? siteDTO.getStatus() : true);
        site.setCreatedAt(LocalDateTime.now());

        siteRepository.save(site);
        log.info("Site created successfully: {}", site);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(site, "Site created", TypesResponse.SUCCESS));
    }

    /**
     * 🔹 Actualizar un sitio existente
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, SiteDTO siteDTO) {
        Optional<Site> existingSite = siteRepository.findById(id);
        if (existingSite.isPresent()) {
            Site site = existingSite.get();

            if (siteDTO.getName() != null) site.setName(siteDTO.getName());
            if (siteDTO.getLocation() != null) site.setLocation(siteDTO.getLocation());
            if (siteDTO.getStatus() != null) site.setStatus(siteDTO.getStatus());

            site.setUpdatedAt(LocalDateTime.now());
            siteRepository.saveAndFlush(site);

            log.info("Site with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(site, "Site updated", TypesResponse.SUCCESS));
        }

        log.warn("Site with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Site not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    /**
     * 🔹 Eliminar un sitio (Soft Delete)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<Site> siteOptional = siteRepository.findById(id);
        if (siteOptional.isPresent()) {
            Site site = siteOptional.get();
           site.setDeletedAt(LocalDateTime.now());
            siteRepository.saveAndFlush(site);

            log.info("Site with id {} soft deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Site deleted (soft delete)", TypesResponse.SUCCESS), HttpStatus.OK);
        }

        log.warn("Site with id {} not found for deletion", id);
        return new ResponseEntity<>(new Message(null, "Site not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }
}
