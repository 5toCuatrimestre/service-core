package jbar.service_core.Style.Controller;

import jbar.service_core.Style.Model.Style;
import jbar.service_core.Style.Model.StyleDTO;
import jbar.service_core.Style.Model.StyleRepository;
import jbar.service_core.Style.Model.UpdateStyleDTO;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Mapper.EntityMapper;
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

    @Transactional
    public ResponseEntity<Message> update(UpdateStyleDTO dto) {
        long startTime = System.nanoTime(); // Inicia la medición en nanosegundos

        Style existingStyle = styleRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Estilo no encontrado"));

        EntityMapper.merge(existingStyle, dto);
        styleRepository.saveAndFlush(existingStyle);

        long endTime = System.nanoTime(); // Finaliza la medición
        long durationInMicroseconds = (endTime - startTime) / 1_000; // Convierte a microsegundos (µs)
        long durationInMilliseconds = durationInMicroseconds / 1_000; // Convierte a milisegundos (ms)

        return new ResponseEntity<>(new Message(
                existingStyle,
                "Style updated in " + durationInMilliseconds + "ms (" + durationInMicroseconds + "µs)",
                TypesResponse.SUCCESS
        ), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> updateLento(StyleDTO styleDTO) {
        long startTime = System.nanoTime(); // Inicia la medición de tiempo
        try {
            Optional<Style> existingStyle = styleRepository.findById(styleDTO.getStyleId());
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
                log.info("Style with id {} updated successfully", styleDTO.getStyleId());

                long endTime = System.nanoTime(); // Finaliza la medición de tiempo
                long durationInMicroseconds = (endTime - startTime) / 1_000; // Convierte a microsegundos
                long durationInMilliseconds = durationInMicroseconds / 1_000; // Convierte a milisegundos

                return new ResponseEntity<>(new Message(
                        style,
                        "Style updated in " + durationInMilliseconds + "ms (" + durationInMicroseconds + "µs)",
                        TypesResponse.SUCCESS
                ), HttpStatus.OK);
            } else {
                log.warn("Style with id {} not found for update", styleDTO.getStyleId());
                return new ResponseEntity<>(new Message(null, "Style not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error updating style with id {}: {}", styleDTO.getStyleId(), e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating style", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
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
