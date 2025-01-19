package jbar.service_core.Position.Controller;

import jbar.service_core.Position.Model.PositionDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
public class PositionController {

    private final jbar.service_core.Position.Service.PositionService positionService;

    @Autowired
    public PositionController(jbar.service_core.Position.Service.PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllPositions() {
        return positionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getPositionById(@PathVariable Integer id) {
        return positionService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createPosition(@RequestBody PositionDTO positionDTO) {
        return positionService.create(positionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updatePosition(@PathVariable Integer id, @RequestBody PositionDTO positionDTO) {
        return positionService.update(id, positionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deletePosition(@PathVariable Integer id) {
        return positionService.delete(id);
    }
}
