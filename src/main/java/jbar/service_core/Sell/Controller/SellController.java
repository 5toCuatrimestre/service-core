package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sell")
public class SellController {

    private final SellService sellService;

    @Autowired
    public SellController(SellService sellService) {
        this.sellService = sellService;
    }

    // Obtener todas las ventas
    @GetMapping("/all")
    public ResponseEntity<Message> getAllSells() {
        return sellService.findAll();
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getSellById(@PathVariable Integer id) {
        return sellService.findById(id);
    }

    // Obtener ventas por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<Message> getSellsByUser(@PathVariable Integer userId) {
        return sellService.findByUserId(userId);
    }

    // Obtener ventas por rango de fechas
    @GetMapping("/by-date")
    public ResponseEntity<Message> getSellsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        return sellService.findByDateRange(startDate, endDate);
    }

    // Crear una venta
    @PostMapping
    public ResponseEntity<Message> createSell(@RequestBody SellDTO sellDTO) {
        return sellService.create(sellDTO);
    }

    // Actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateSell(@PathVariable Integer id, @RequestBody SellDTO sellDTO) {
        return sellService.update(id, sellDTO);
    }

    // Cambiar el estado de una venta (Soft Delete)
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeSellStatus(@PathVariable Integer id) {
        return sellService.changeStatus(id);
    }
}
