package jbar.service_core.Style.Controller;

import jbar.service_core.Style.Model.Style;
import jbar.service_core.Style.Model.StyleDTO;
import jbar.service_core.Style.Model.StyleRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StyleService {
    private final Logger log = LoggerFactory.getLogger(StyleService.class);
    private final StyleRepository styleRepository;

    @Autowired
    public StyleService(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Style> styles = styleRepository.findAll();
        log.info("All styles retrieved successfully");
        return new ResponseEntity<>(new Message(styles, "Styles retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Style> style = styleRepository.findById(id);
        return style.map(value -> {
            log.info("Style with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Style found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Style with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(StyleDTO styleDTO) {
        Style style = new Style();
        style.setName(styleDTO.getName());
        style.setDescription(styleDTO.getDescription());
        style.setStatus(styleDTO.getStatus());
        styleRepository.save(style);

        log.info("Style created successfully: {}", style);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(style, "Style created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, StyleDTO styleDTO) {
        Optional<Style> existingStyle = styleRepository.findById(id);
        if (existingStyle.isPresent()) {
            Style style = existingStyle.get();
            if (styleDTO.getName() != null && !styleDTO.getName().isEmpty()) {
                style.setName(styleDTO.getName());
            }
            if (styleDTO.getDescription() != null) {
                style.setDescription(styleDTO.getDescription());
            }
            if (styleDTO.getStatus() != null) {
                style.setStatus(styleDTO.getStatus());
            }

            styleRepository.save(style);
            log.info("Style with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(style, "Style updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Style with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<Style> style = styleRepository.findById(id);
        if (style.isPresent()) {
            styleRepository.delete(style.get());
            log.info("Style with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Style deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Style with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
