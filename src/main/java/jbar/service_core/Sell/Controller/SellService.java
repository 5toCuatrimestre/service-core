package jbar.service_core.Sell.Controller;

import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
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
    private final ProductRepository productRepository;

    @Autowired
    public SellService(SellRepository sellRepository, ProductRepository productRepository) {
        this.sellRepository = sellRepository;
        this.productRepository = productRepository;
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
        try {
            // Buscar el producto por su ID
            Optional<Product> product = productRepository.findById(sellDTO.getProductId());
            if (product.isEmpty()) {
                log.warn("Product with id {} not found", sellDTO.getProductId());
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            // Crear la entidad Sell
            Sell sell = new Sell();
            sell.setProduct(product.get()); // Asignar el objeto Product
            sell.setQuantity(sellDTO.getQuantity());
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate());
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

            Optional<Product> product = productRepository.findById(sellDTO.getProductId());
            if (product.isEmpty()) {
                log.warn("Product with id {} not found", sellDTO.getProductId());
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Sell sell = existingSell.get();
            sell.setProduct(product.get()); // Asignar el objeto Product
            sell.setQuantity(sellDTO.getQuantity());
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate());
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
            sellRepository.delete(sell.get());
            log.info("Sell with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Sell deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Sell with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
