package jbar.service_core.RatingUserSell.Controller;

import jbar.service_core.Util.Response.Message;
import jbar.service_core.RatingUserSell.Model.RatingUserSellDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating-user-sell")
public class RatingUserSellController {
    private final RatingUserSellService ratingUserSellService;

    @Autowired
    public RatingUserSellController(RatingUserSellService ratingUserSellService) {
        this.ratingUserSellService = ratingUserSellService;
    }

    // Obtener todas las calificaciones
    @GetMapping("/all")
    public ResponseEntity<Message> getAllRatings() {
        return ratingUserSellService.findAll();
    }

    // Obtener calificaciones por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<Message> getRatingsByUser(@PathVariable Integer userId) {
        return ratingUserSellService.findByUserId(userId);
    }

    // Obtener calificaciones por venta
    @GetMapping("/sell/{sellId}")
    public ResponseEntity<Message> getRatingsBySell(@PathVariable Integer sellId) {
        return ratingUserSellService.findBySellId(sellId);
    }

    // Crear una nueva calificación
    @PostMapping
    public ResponseEntity<Message> createRating(@RequestBody RatingUserSellDTO ratingDTO) {
        return ratingUserSellService.create(ratingDTO);
    }

}
