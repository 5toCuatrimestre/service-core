package jbar.service_core.Style.Controller;

import jbar.service_core.Style.Model.StyleDTO;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/style")
public class StyleController {

    private final StyleService styleService;

    @Autowired
    public StyleController(StyleService styleService) {
        this.styleService = styleService;
    }

    /**
     * Get all styles.
     * @return List of styles.
     */
    @GetMapping("/all")
    public ResponseEntity<Message> getAllStyles() {
        return styleService.findAll();
    }

    /**
     * Get a specific style by ID.
     * @param id ID of the style.
     * @return Style details if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getStyleById(@PathVariable Integer id) {
        return styleService.findById(id);
    }

    /**
     * Create a new style.
     * @param styleDTO Data for the new style.
     * @return Created style response.
     */
    @PostMapping
    public ResponseEntity<Message> createStyle(@RequestBody StyleDTO styleDTO) {
        if (styleDTO == null) {
            return ResponseEntity.badRequest().body(new Message(null, "Invalid request body", TypesResponse.ERROR));
        }
        return styleService.create(styleDTO);
    }

    /**
     * Update an existing style.
     * @param id ID of the style to update.
     * @param styleDTO New data for the style.
     * @return Updated style response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateStyle(@PathVariable Integer id, @RequestBody StyleDTO styleDTO) {
        if (styleDTO == null) {
            return ResponseEntity.badRequest().body(new Message(null, "Invalid request body", TypesResponse.ERROR));
        }
        return styleService.update(id, styleDTO);
    }

    /**
     * Change status of a style (Soft Delete).
     * @param id ID of the style.
     * @return Response indicating success or failure.
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeStyleStatus(@PathVariable Integer id) {
        return styleService.changeStatus(id);
    }
}
