package jbar.service_core.Style.Controller;

import jbar.service_core.Style.Model.StyleDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity<Message> getAllStyles() {
        return styleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getStyleById(@PathVariable Integer id) {
        return styleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createStyle(@RequestBody StyleDTO styleDTO) {
        return styleService.create(styleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateStyle(@PathVariable Integer id, @RequestBody StyleDTO styleDTO) {
        styleDTO.setStyleId(id);
        return styleService.updateLento(styleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteStyle(@PathVariable Integer id) {
        return styleService.delete(id);
    }
}
