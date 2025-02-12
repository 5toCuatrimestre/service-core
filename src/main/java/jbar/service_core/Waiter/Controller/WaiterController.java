package jbar.service_core.Waiter.Controller;

import jbar.service_core.Util.Response.Message;
import jbar.service_core.Waiter.Model.WaiterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waiter")
public class WaiterController {
    private final WaiterService waiterService;

    @Autowired
    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllWaiters() {
        return waiterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getWaiterById(@PathVariable Integer id) {
        return waiterService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createWaiter(@RequestBody WaiterDTO waiterDTO) {
        return waiterService.create(waiterDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteWaiter(@PathVariable Integer id) {
        return waiterService.delete(id);
    }
}
