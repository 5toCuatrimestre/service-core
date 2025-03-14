package jbar.service_core.Position_Site.Controller;

import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Position_Site.Service.PositionSiteRepository;
import jbar.service_core.Position_Site.Service.PositionSiteDTO;
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
public class PositionSiteService {
    private final Logger log = LoggerFactory.getLogger(PositionSiteService.class);
    private final PositionSiteRepository positionSiteRepository;

    @Autowired
    public PositionSiteService(PositionSiteRepository positionSiteRepository) {
        this.positionSiteRepository = positionSiteRepository;
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
        PositionSite positionSite = new PositionSite();
        positionSite.setPosition(positionSiteDTO.getPosition());
        positionSite.setSite(positionSiteDTO.getSite());
        positionSite.setCapacity(positionSiteDTO.getCapacity());
        positionSite.setXLocation(positionSiteDTO.getXLocation());
        positionSite.setYLocation(positionSiteDTO.getYLocation());
        positionSite.setCreatedAt(LocalDateTime.now());

        positionSiteRepository.saveAndFlush(positionSite);

        log.info("PositionSite created successfully: {}", positionSite);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(positionSite, "PositionSite created", TypesResponse.SUCCESS));
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
