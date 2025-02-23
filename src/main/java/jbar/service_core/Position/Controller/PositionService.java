package jbar.service_core.Position.Controller;

import jbar.service_core.Position.Model.Position;
import jbar.service_core.Position.Model.PositionDTO;
import jbar.service_core.Position.Model.PositionRepository;
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
public class PositionService {
    private final Logger log = LoggerFactory.getLogger(PositionService.class);
    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Position> positions = positionRepository.findAll();
        log.info("All positions retrieved successfully");
        return new ResponseEntity<>(new Message(positions, "Positions retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(value -> {
            log.info("Position with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Position found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Position with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Position not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(PositionDTO positionDTO) {
        Position position = new Position();
        position.setName(positionDTO.getName());
        position.setDescription(positionDTO.getDescription());
        position.setStatus(positionDTO.getStatus());
        positionRepository.save(position);

        log.info("Position created successfully: {}", position);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(position, "Position created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, PositionDTO positionDTO) {
        Optional<Position> existingPosition = positionRepository.findById(id);
        if (existingPosition.isPresent()) {
            Position position = existingPosition.get();
            position.setName(positionDTO.getName());
            position.setDescription(positionDTO.getDescription());
            position.setStatus(positionDTO.getStatus());
            position.setUpdatedAt(LocalDateTime.now());

            positionRepository.saveAndFlush(position);

            log.info("Position with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(position, "Position updated", TypesResponse.SUCCESS));
        }

        log.warn("Position with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Position not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            positionRepository.delete(position.get());
            log.info("Position with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Position deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Position with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Position not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
