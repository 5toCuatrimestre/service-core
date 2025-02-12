package jbar.service_core.Rating.Controller;

import jbar.service_core.Rating.Model.Rating;
import jbar.service_core.Rating.Model.RatingDTO;
import jbar.service_core.Rating.Model.RatingRepository;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Waiter.Model.Waiter;
import jbar.service_core.Waiter.Model.WaiterRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private WaiterRepository waiterRepository;

    public ResponseEntity<Message> findAll() {
        List<Rating> ratings = ratingRepository.findAll();
        return new ResponseEntity<>(new Message(ratings, "Ratings retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findByWaiterId(Integer waiterId) {
        List<Rating> ratings = ratingRepository.findByWaiterWaiterId(waiterId);
        return new ResponseEntity<>(new Message(ratings, "Ratings for waiter retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> create(RatingDTO ratingDTO) {
        Optional<Sell> sell = sellRepository.findById(ratingDTO.getSellId());
        if (sell.isEmpty()) {
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Optional<Waiter> waiter = waiterRepository.findById(ratingDTO.getWaiterId());
        if (waiter.isEmpty()) {
            return new ResponseEntity<>(new Message(null, "Waiter not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Rating rating = new Rating();
        rating.setSell(sell.get());
        rating.setWaiter(waiter.get());
        rating.setStars(ratingDTO.getStars());
        rating.setComment(ratingDTO.getComment());

        ratingRepository.save(rating);
        return new ResponseEntity<>(new Message(rating, "Rating created successfully", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            ratingRepository.delete(rating.get());
            return new ResponseEntity<>(new Message(null, "Rating deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "Rating not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
