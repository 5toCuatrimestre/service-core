package jbar.service_core.Position_Site.Controller;

import jbar.service_core.Position_Site.Service.PositionSiteDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position-site")
public class PositionSiteController {

    private final PositionSiteService positionSiteService;

    @Autowired
    public PositionSiteController(PositionSiteService positionSiteService) {
        this.positionSiteService = positionSiteService;
    }

    /**
     * 🔹 Obtener todos los PositionSites activos
     */
    @GetMapping("/all")
    public ResponseEntity<Message> getAllPositionSites() {
        return positionSiteService.findAll();
    }

    /**
     * 🔹 Obtener un PositionSite por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getPositionSiteById(@PathVariable Integer id) {
        return positionSiteService.findById(id);
    }

    /**
     * 🔹 Crear un nuevo PositionSite
     */
    @PostMapping
    public ResponseEntity<Message> createPositionSite(@RequestBody PositionSiteDTO positionSiteDTO) {
        return positionSiteService.create(positionSiteDTO);
    }

    /**
     * 🔹 Actualizar un PositionSite
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updatePositionSite(@PathVariable Integer id, @RequestBody PositionSiteDTO positionSiteDTO) {
        return positionSiteService.update(id, positionSiteDTO);
    }

    /**
     * 🔹 Eliminar un PositionSite (Soft Delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deletePositionSite(@PathVariable Integer id) {
        return positionSiteService.delete(id);
    }
}