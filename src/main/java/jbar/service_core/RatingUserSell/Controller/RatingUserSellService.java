package jbar.service_core.RatingUserSell.Controller;

import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import jbar.service_core.RatingUserSell.Model.RatingUserSellDTO;
import jbar.service_core.RatingUserSell.Model.RatingUserSellRepository;
import jbar.service_core.RatingUserSell.Model.WaiterRatingChartDTO;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RatingUserSellService {
    private final Logger log = LoggerFactory.getLogger(RatingUserSellService.class);
    private final RatingUserSellRepository ratingUserSellRepository;
    private final UserRepository userRepository;
    private final SellRepository sellRepository;

    @Autowired
    public RatingUserSellService(RatingUserSellRepository ratingUserSellRepository, UserRepository userRepository,
            SellRepository sellRepository) {
        this.ratingUserSellRepository = ratingUserSellRepository;
        this.userRepository = userRepository;
        this.sellRepository = sellRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<RatingUserSell> ratings = ratingUserSellRepository.findAll();
        log.info("All ratings retrieved successfully");
        return new ResponseEntity<>(new Message(ratings, "Ratings retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByUserId(Integer userId) {
        List<RatingUserSell> ratings = ratingUserSellRepository.findByUser_UserId(userId);
        return new ResponseEntity<>(new Message(ratings, "Ratings found for user", TypesResponse.SUCCESS),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findBySellId(Integer sellId) {
        List<RatingUserSell> ratings = ratingUserSellRepository.findBySell_SellId(sellId);
        return new ResponseEntity<>(new Message(ratings, "Ratings found for sell", TypesResponse.SUCCESS),
                HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(RatingUserSellDTO ratingDTO) {
        Optional<User> user = userRepository.findById(ratingDTO.getUserId());
        if (user.isEmpty()) {
            log.warn("User with id {} not found", ratingDTO.getUserId());
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Optional<Sell> sell = sellRepository.findById(ratingDTO.getSellId());
        if (sell.isEmpty()) {
            log.warn("Sell with id {} not found", ratingDTO.getSellId());
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        RatingUserSell ratingUserSell = new RatingUserSell();
        ratingUserSell.setUser(user.get());
        ratingUserSell.setSell(sell.get());
        ratingUserSell.setStars(ratingDTO.getStars());

        ratingUserSellRepository.save(ratingUserSell);
        log.info("Rating created successfully: {}", ratingUserSell);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(ratingUserSell, "Rating created", TypesResponse.SUCCESS));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getWaiterRatingsChart(Date startDate, Date endDate) {
        List<Object[]> result = ratingUserSellRepository.findTotalStarsGroupedByWaiter(startDate, endDate);
    
        Map<String, Integer> chartData = new LinkedHashMap<>();
        for (Object[] row : result) {
            String name = (String) row[0];
            Integer totalStars = ((Number) row[1]).intValue();
            chartData.put(name, totalStars);
        }
    
        WaiterRatingChartDTO dto = new WaiterRatingChartDTO(chartData);
        return new ResponseEntity<>(new Message(List.of(dto), "Waiter ratings chart generated", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    
}
