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

import java.time.LocalDateTime;
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

    /**
     * Retrieve all styles.
     * @return List of styles.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Style> styles = styleRepository.findAll();
        log.info("All styles retrieved successfully");
        return ResponseEntity.ok(new Message(styles, "Styles retrieved", TypesResponse.SUCCESS));
    }

    /**
     * Retrieve a style by ID.
     * @param id Style ID.
     * @return Style details if found.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        return styleRepository.findById(id)
                .map(style -> {
                    log.info("Style with id {} retrieved successfully", id);
                    return ResponseEntity.ok(new Message(style, "Style found", TypesResponse.SUCCESS));
                })
                .orElseGet(() -> {
                    log.warn("Style with id {} not found", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new Message(null, "Style not found", TypesResponse.ERROR));
                });
    }

    /**
     * Create a new style.
     * @param styleDTO Style data.
     * @return Created style response.
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(StyleDTO styleDTO) {
        Style style = new Style();
        style.setStatus(styleDTO.getStatus());
        style.setH1(styleDTO.getH1());
        style.setH2(styleDTO.getH2());
        style.setH3(styleDTO.getH3());
        style.setP(styleDTO.getP());
        style.setBgCard(styleDTO.getBgCard()); // 🔹 Evitar null
        style.setBgInterface(styleDTO.getBgInterface());
        style.setBgButton(styleDTO.getBgButton());

        styleRepository.save(style);

        log.info("Style created successfully: {}", style);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(style, "Style created", TypesResponse.SUCCESS));
    }

    /**
     * Update an existing style.
     * @param id Style ID.
     * @param styleDTO New style data.
     * @return Updated style response.
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, StyleDTO styleDTO) {
        //sout para cada atrbuto de styleDTO
        System.out.println(styleDTO.getStatus());
        System.out.println(styleDTO.getH1());
        System.out.println(styleDTO.getH2());
        System.out.println(styleDTO.getH3());
        System.out.println(styleDTO.getP());
        System.out.println(styleDTO.getBgCard());
        System.out.println(styleDTO.getBgInterface());
        System.out.println(styleDTO.getBgButton());
        System.out.println(styleDTO.getBgCard());
        return styleRepository.findById(id)
                .map(style -> {
                    // Primero, poner en false el estado de todos los estilos
                    styleRepository.findAll().forEach(existingStyle -> {
                        if (!existingStyle.getStyleId().equals(id)) {
                            existingStyle.setStatus(false); // Actualizamos el estado a false
                            styleRepository.save(existingStyle); // Guardamos los cambios
                        }
                    });

                    // Actualizamos los campos directamente desde el DTO
                    style.setStatus(styleDTO.getStatus());
                    style.setH1(styleDTO.getH1());
                    style.setH2(styleDTO.getH2());
                    style.setH3(styleDTO.getH3());
                    style.setP(styleDTO.getP());
                    style.setBgCard(styleDTO.getBgCard());
                    style.setBgInterface(styleDTO.getBgInterface());
                    style.setBgButton(styleDTO.getBgButton());

                    // Actualizamos la fecha de actualización
                    style.setUpdatedAt(LocalDateTime.now());

                    // Guardamos el estilo actualizado
                    styleRepository.save(style);

                    log.info("Style with id {} updated successfully", id);
                    return ResponseEntity.ok(new Message(style, "Style updated", TypesResponse.SUCCESS));
                })
                .orElseGet(() -> {
                    log.warn("Style with id {} not found for update", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new Message(null, "Style not found", TypesResponse.ERROR));
                });
    }

    /**
     * Change the status of a style (Soft Delete).
     * @param id Style ID.
     * @return Response indicating success or failure.
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        return styleRepository.findById(id)
                .map(style -> {
                    style.setStatus(!style.getStatus()); // Toggle status
                    style.setDeletedAt(style.getStatus() ? null : LocalDateTime.now());

                    styleRepository.save(style);
                    log.info("Style with id {} status changed", id);
                    return ResponseEntity.ok(new Message(null, "Style status changed", TypesResponse.SUCCESS));
                })
                .orElseGet(() -> {
                    log.warn("Style with id {} not found for status change", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new Message(null, "Style not found", TypesResponse.ERROR));
                });
    }

    /**
     * Activate a style and deactivate all others.
     * @param id Style ID.
     * @return Response indicating success or failure.
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> activateStyle(Integer id) {
        return styleRepository.findById(id)
                .map(style -> {
                    // Deactivate all other styles
                    styleRepository.deactivateAllStylesExcept(id);

                    // Activate selected style
                    style.setStatus(true);
                    style.setUpdatedAt(LocalDateTime.now());
                    styleRepository.save(style);

                    log.info("Style with id {} activated successfully", id);
                    return ResponseEntity.ok(new Message(style, "Style activated and others deactivated", TypesResponse.SUCCESS));
                })
                .orElseGet(() -> {
                    log.warn("Style with id {} not found for activation", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new Message(null, "Style not found", TypesResponse.ERROR));
                });
    }
}
