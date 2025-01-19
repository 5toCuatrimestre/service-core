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

import java.util.List;
import java.util.Optional;

@Service
public class StyleService {
    private final Logger log = LoggerFactory.getLogger(StyleService.class);
    private final StyleRepository styleRepository;

    @Autowired
    public StyleService(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Style> styles = styleRepository.findAll();
        log.info("All styles retrieved successfully");
        return new ResponseEntity<>(new Message(styles, "Styles retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Style> style = styleRepository.findById(id);
        if (style.isPresent()) {
            log.info("Style with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(style.get(), "Style found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Style with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(StyleDTO styleDTO) {
        Style style = new Style();
        style.setName(styleDTO.getName());
        style.setDescription(styleDTO.getDescription());
        style.setStatus(styleDTO.getStatus());
        styleRepository.save(style);
        log.info("Style created successfully: {}", style);
        return new ResponseEntity<>(new Message(style, "Style created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, StyleDTO styleDTO) {
        Optional<Style> existingStyle = styleRepository.findById(id);
        if (existingStyle.isPresent()) {
            Style style = existingStyle.get();
            style.setName(styleDTO.getName());
            style.setDescription(styleDTO.getDescription());
            style.setStatus(styleDTO.getStatus());
            styleRepository.save(style);
            log.info("Style with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(style, "Style updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Style with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

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
