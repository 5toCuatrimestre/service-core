package jbar.service_core.RatingUserSell.Controller;

import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.RatingUserSell.Model.RatingUserSellDTO;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.sql.Date;
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

    @GetMapping("/chart/waiter-ratings")
    public ResponseEntity<Message> getWaiterRatingsChart(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            java.util.Date parsedStart = dateFormat.parse(startDate);
            java.util.Date parsedEnd = dateFormat.parse(endDate);

            // Convertimos a java.sql.Date para que sea compatible con tu entidad
            Date sqlStartDate = new Date(parsedStart.getTime());
            Date sqlEndDate = new Date(parsedEnd.getTime());

            return ratingUserSellService.getWaiterRatingsChart(sqlStartDate, sqlEndDate);

        } catch (ParseException e) {
            return new ResponseEntity<>(
                    new Message(null, "Invalid date format. Use yyyy-MM-dd", TypesResponse.ERROR),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
