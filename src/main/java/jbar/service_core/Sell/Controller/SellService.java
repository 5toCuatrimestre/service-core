package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SellService {
    private final Logger log = LoggerFactory.getLogger(SellService.class);
    private final SellRepository sellRepository;
    private final UserRepository userRepository;

    @Autowired
    public SellService(SellRepository sellRepository, UserRepository userRepository) {
        this.sellRepository = sellRepository;
        this.userRepository = userRepository;
    }

    // Obtener todas las ventas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Sell> sells = sellRepository.findAll();
        return new ResponseEntity<>(new Message(sells, "All sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener venta por ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        return sell.map(value -> new ResponseEntity<>(new Message(value, "Sell found", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // Obtener ventas por usuario
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByUserId(Integer userId) {
        List<Sell> sells = sellRepository.findByUserUserId(userId);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found for user", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener ventas en un rango de fechas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Crear una venta
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(SellDTO sellDTO) {
        try {
            Optional<User> user = userRepository.findById(sellDTO.getUserId());
            if (user.isEmpty()) {
                return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Sell sell = new Sell();
            sell.setUser(user.get());
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

    // Actualizar una venta
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, SellDTO sellDTO) {
        try {
            Optional<Sell> existingSell = sellRepository.findById(id);
            if (existingSell.isEmpty()) {
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

    // Cambiar el estado de una venta (Soft Delete)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        if (sell.isPresent()) {
            Sell existingSell = sell.get();
            if (existingSell.getStatus()) {
                existingSell.setStatus(false);
                existingSell.setDeletedAt(LocalDateTime.now());
            } else {
                existingSell.setStatus(true);
                existingSell.setDeletedAt(null);
            }

            sellRepository.save(existingSell);
            log.info("Sell with id {} status changed", id);
            return new ResponseEntity<>(new Message(null, "Sell status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
