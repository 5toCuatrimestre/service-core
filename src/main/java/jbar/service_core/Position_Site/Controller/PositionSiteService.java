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

import java.util.List;
import java.util.Optional;

@Service
public class PositionSiteService {
    private final Logger log = LoggerFactory.getLogger(PositionSiteService.class);
    private final PositionSiteRepository positionSiteRepository;

    @Autowired
    public PositionSiteService(PositionSiteRepository positionSiteRepository) {
        this.positionSiteRepository = positionSiteRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<PositionSite> positionSites = positionSiteRepository.findAll();
        log.info("All PositionSites retrieved successfully");
        return new ResponseEntity<>(new Message(positionSites, "PositionSites retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<PositionSite> positionSite = positionSiteRepository.findById(id);
        if (positionSite.isPresent()) {
            log.info("PositionSite with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(positionSite.get(), "PositionSite found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("PositionSite with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "PositionSite not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(PositionSiteDTO positionSiteDTO) {
        PositionSite positionSite = new PositionSite();
        positionSite.setPositionSiteId(positionSiteDTO.getPositionId());
        positionSite.setPositionSiteId(positionSiteDTO.getSiteId());
        positionSite.setCapacity(positionSiteDTO.getCapacity());
        positionSiteRepository.save(positionSite);
        log.info("PositionSite created successfully: {}", positionSite);
        return new ResponseEntity<>(new Message(positionSite, "PositionSite created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<PositionSite> positionSite = positionSiteRepository.findById(id);
        if (positionSite.isPresent()) {
            positionSiteRepository.delete(positionSite.get());
            log.info("PositionSite with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "PositionSite deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("PositionSite with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "PositionSite not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
