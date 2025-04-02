package jbar.service_core.Style.Controller;

import jbar.service_core.Style.Model.StyleDTO;
import jbar.service_core.Util.Enum.TypesResponse;
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

    /**
     * 🔹 Obtener todos los estilos disponibles.
     */
    @GetMapping("/all")
    public ResponseEntity<Message> getAllStyles() {
        return styleService.findAll();
    }

    /**
     * 🔹 Obtener un estilo específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getStyleById(@PathVariable Integer id) {
        return styleService.findById(id);
    }

    /**
     * 🔹 Crear un nuevo estilo.
     */
    @PostMapping
    public ResponseEntity<Message> createStyle(@RequestBody StyleDTO styleDTO) {
        if (styleDTO == null) {
            return ResponseEntity.badRequest().body(new Message(null, "Invalid request body", TypesResponse.ERROR));
        }
        return styleService.create(styleDTO);
    }

    /**
     * 🔹 Actualizar un estilo existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateStyle(@PathVariable Integer id, @RequestBody StyleDTO styleDTO) {
        if (styleDTO == null) {
            return ResponseEntity.badRequest().body(new Message(null, "Invalid request body", TypesResponse.ERROR));
        }
        return styleService.update(id, styleDTO);
    }

    /**
     * 🔹 Activar un estilo y desactivar los demás automáticamente.
     */
    @PutMapping("/activate/{id}")
    public ResponseEntity<Message> activateStyle(@PathVariable Integer id) {
        return styleService.activateStyle(id);
    }

    /**
     * 🔹 Cambiar el estado de un estilo (Soft Delete).
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeStyleStatus(@PathVariable Integer id) {
        return styleService.changeStatus(id);
    }
}
