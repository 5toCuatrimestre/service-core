package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SellService {
    private final Logger log = LoggerFactory.getLogger(SellService.class);
    private final SellRepository sellRepository;

    @Autowired
    public SellService(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Sell> sells = sellRepository.findAll();
        log.info("All sells retrieved successfully");
        return new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        if (sell.isPresent()) {
            log.info("Sell with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(sell.get(), "Sell found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Sell with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> findByUserId(Integer userId) {
        List<Sell> sells = sellRepository.findByUser_UserId(userId);
        if (sells.isEmpty()) {
            log.warn("No sells found for user with id {}", userId);
            return new ResponseEntity<>(new Message(null, "No sells found for user", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        log.info("Sells retrieved for user {}", userId);
        return new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findByWaiterId(Integer waiterId) {
        List<Sell> sells = sellRepository.findByWaiter_WaiterId(waiterId);
        if (sells.isEmpty()) {
            log.warn("No sells found for waiter with id {}", waiterId);
            return new ResponseEntity<>(new Message(null, "No sells found for waiter", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        log.info("Sells retrieved for waiter {}", waiterId);
        return new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);
        if (sells.isEmpty()) {
            log.warn("No sells found in date range {} - {}", startDate, endDate);
            return new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        log.info("Sells retrieved in date range {} - {}", startDate, endDate);
        return new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findActiveSells() {
        List<Sell> sells = sellRepository.findByStatusTrue();
        if (sells.isEmpty()) {
            log.warn("No active sells found");
            return new ResponseEntity<>(new Message(null, "No active sells found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        log.info("Active sells retrieved successfully");
        return new ResponseEntity<>(new Message(sells, "Active sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findInactiveSells() {
        List<Sell> sells = sellRepository.findByStatusFalse();
        if (sells.isEmpty()) {
            log.warn("No inactive sells found");
            return new ResponseEntity<>(new Message(null, "No inactive sells found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        log.info("Inactive sells retrieved successfully");
        return new ResponseEntity<>(new Message(sells, "Inactive sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> create(SellDTO sellDTO) {
        try {
            Sell sell = new Sell();
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate());
            sell.setSellTime(sellDTO.getSellTime());
            sell.setStatus(sellDTO.getStatus());
            sellRepository.save(sell);

            log.info("Sell created successfully: {}", sell);
            return new ResponseEntity<>(new Message(sell, "Sell created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating sell: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating sell", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> update(Integer id, SellDTO sellDTO) {
        try {
            Optional<Sell> existingSell = sellRepository.findById(id);
            if (existingSell.isEmpty()) {
                log.warn("Sell with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Sell sell = existingSell.get();
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate());
            sell.setSellTime(sellDTO.getSellTime());
            sell.setStatus(sellDTO.getStatus());
            sellRepository.save(sell);

            log.info("Sell with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(sell, "Sell updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating sell with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating sell", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        if (sell.isPresent()) {
            Sell existingSell = sell.get();
            existingSell.setStatus(false);
            sellRepository.save(existingSell);
            log.info("Sell with id {} marked as deleted", id);
            return new ResponseEntity<>(new Message(null, "Sell deleted (soft delete)", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Sell with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
