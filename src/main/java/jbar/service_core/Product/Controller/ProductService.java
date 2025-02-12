package jbar.service_core.Product.Controller;

import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductDTO;
import jbar.service_core.Product.Model.ProductRepository;
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
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Product> products = productRepository.findAll();
        log.info("All products retrieved successfully");
        return new ResponseEntity<>(new Message(products, "Products retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> {
            log.info("Product with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Product found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Product with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.getStatus() != null ? productDTO.getStatus() : true);
        productRepository.save(product);

        log.info("Product created successfully: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(product, "Product created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStatus(productDTO.getStatus());
            product.setUpdatedAt(LocalDateTime.now());

            productRepository.saveAndFlush(product);

            log.info("Product with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(product, "Product updated", TypesResponse.SUCCESS));
        }

        log.warn("Product with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            log.info("Product with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Product deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Product with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
