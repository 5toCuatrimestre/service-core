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

    public ResponseEntity<Message> create(SellDTO sellDTO) {
        Sell sell = new Sell();
        sell.setProduct(sellDTO.getProduct());
        sell.setQuantity(sellDTO.getQuantity());
        sell.setTotalPrice(sellDTO.getTotalPrice());
        sell.setSellDate(sellDTO.getSellDate());
        sell.setStatus(sellDTO.getStatus());
        sellRepository.save(sell);
        log.info("Sell created successfully: {}", sell);
        return new ResponseEntity<>(new Message(sell, "Sell created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, SellDTO sellDTO) {
        Optional<Sell> existingSell = sellRepository.findById(id);
        if (existingSell.isPresent()) {
            Sell sell = existingSell.get();
            sell.setProduct(sellDTO.getProduct());
            sell.setQuantity(sellDTO.getQuantity());
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate());
            sell.setStatus(sellDTO.getStatus());
            sellRepository.save(sell);
            log.info("Sell with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(sell, "Sell updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Sell with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        if (sell.isPresent()) {
            sellRepository.delete(sell.get());
            log.info("Sell with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Sell deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Sell with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
