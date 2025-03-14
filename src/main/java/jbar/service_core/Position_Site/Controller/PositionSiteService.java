package jbar.service_core.Position_Site.Controller;

import jbar.service_core.Position.Model.Position;
import jbar.service_core.Position.Model.PositionRepository;
import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Position_Site.Service.PositionSiteDTO;
import jbar.service_core.Position_Site.Service.PositionSiteRepository;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Site.Model.SiteRepository;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
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
public class PositionSiteService {
    private final Logger log = LoggerFactory.getLogger(PositionSiteService.class);
    private final PositionSiteRepository positionSiteRepository;
    private final PositionRepository positionRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public PositionSiteService(
            PositionSiteRepository positionSiteRepository,
            PositionRepository positionRepository,
            SiteRepository siteRepository
    ) {
        this.positionSiteRepository = positionSiteRepository;
        this.positionRepository = positionRepository;
        this.siteRepository = siteRepository;
    }

    /**
     * 🔹 Obtener todos los PositionSites
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<PositionSite> positionSites = positionSiteRepository.findByDeletedAtIsNull();
        log.info("All active PositionSites retrieved successfully");
        return new ResponseEntity<>(new Message(positionSites, "Active PositionSites retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    /**
     * 🔹 Obtener un PositionSite por ID
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<PositionSite> positionSite = positionSiteRepository.findById(id);
        return positionSite.map(value -> {
            log.info("PositionSite with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "PositionSite found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("PositionSite with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "PositionSite not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    /**
     * 🔹 Crear un nuevo PositionSite
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(PositionSiteDTO positionSiteDTO) {
        log.info("Creating PositionSite with DTO: {}", positionSiteDTO);

        // 1️⃣ Validamos si Position y Site existen en la BD
        Optional<Position> positionOptional = positionRepository.findById(positionSiteDTO.getPositionId());
        Optional<Site> siteOptional = siteRepository.findById(positionSiteDTO.getSiteId());

        if (positionOptional.isEmpty()) {
            log.error("Position with ID {} not found", positionSiteDTO.getPositionId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(null, "Position not found", TypesResponse.ERROR));
        }

        if (siteOptional.isEmpty()) {
            log.error("Site with ID {} not found", positionSiteDTO.getSiteId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(null, "Site not found", TypesResponse.ERROR));
        }

        // 2️⃣ Creamos la entidad con valores del DTO
        PositionSite positionSite = new PositionSite();
        positionSite.setPosition(positionOptional.get());
        positionSite.setSite(siteOptional.get());
        positionSite.setCapacity(positionSiteDTO.getCapacity() != null ? positionSiteDTO.getCapacity() : 0);
        positionSite.setXLocation(positionSiteDTO.getXLocation() != null ? positionSiteDTO.getXLocation() : 0.0);
        positionSite.setYLocation(positionSiteDTO.getYLocation() != null ? positionSiteDTO.getYLocation() : 0.0);
        positionSite.setStatus(true);
        positionSite.setCreatedAt(LocalDateTime.now());

        log.info("Saving PositionSite: {}", positionSite);

        // 3️⃣ Guardamos en la base de datos
        PositionSite savedPositionSite = positionSiteRepository.save(positionSite);

        // 4️⃣ Verificamos si se guardó correctamente
        if (savedPositionSite.getPositionSiteId() == null) {
            log.error("Failed to save PositionSite");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message(null, "Failed to create PositionSite", TypesResponse.ERROR));
        }

        log.info("PositionSite created successfully: {}", savedPositionSite);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(savedPositionSite, "PositionSite created", TypesResponse.SUCCESS));
    }

    /**
     * 🔹 Actualizar un PositionSite existente
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, PositionSiteDTO positionSiteDTO) {
        Optional<PositionSite> existingPositionSite = positionSiteRepository.findById(id);
        if (existingPositionSite.isPresent()) {
            PositionSite positionSite = existingPositionSite.get();

            if (positionSiteDTO.getCapacity() != null) positionSite.setCapacity(positionSiteDTO.getCapacity());
            if (positionSiteDTO.getXLocation() != null) positionSite.setXLocation(positionSiteDTO.getXLocation());
            if (positionSiteDTO.getYLocation() != null) positionSite.setYLocation(positionSiteDTO.getYLocation());

            positionSite.setUpdatedAt(LocalDateTime.now());
            positionSiteRepository.saveAndFlush(positionSite);

            log.info("PositionSite with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(positionSite, "PositionSite updated", TypesResponse.SUCCESS));
        }

        log.warn("PositionSite with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "PositionSite not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    /**
     * 🔹 Eliminar un PositionSite (Soft Delete)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<PositionSite> positionSiteOptional = positionSiteRepository.findById(id);
        if (positionSiteOptional.isPresent()) {
            PositionSite positionSite = positionSiteOptional.get();
            positionSite.setDeletedAt(LocalDateTime.now()); // Soft delete
            positionSiteRepository.save(positionSite);

            log.info("PositionSite with id {} soft deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "PositionSite deleted (soft delete)", TypesResponse.SUCCESS), HttpStatus.OK);
        }

        log.warn("PositionSite with id {} not found for deletion", id);
        return new ResponseEntity<>(new Message(null, "PositionSite not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }
}
