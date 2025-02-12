package jbar.service_core.Rating.Controller;

import jbar.service_core.Util.Response.Message;
import jbar.service_core.Rating.Model.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllRatings() {
        return ratingService.findAll();
    }

    @GetMapping("/waiter/{waiterId}")
    public ResponseEntity<Message> getRatingsByWaiter(@PathVariable Integer waiterId) {
        return ratingService.findByWaiterId(waiterId);
    }

    @PostMapping
    public ResponseEntity<Message> createRating(@RequestBody RatingDTO ratingDTO) {
        return ratingService.create(ratingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteRating(@PathVariable Integer id) {
        return ratingService.delete(id);
    }
}
