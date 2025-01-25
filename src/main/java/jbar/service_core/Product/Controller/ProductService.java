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

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ResponseEntity<Message> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            log.info("All products retrieved successfully");
            return new ResponseEntity<>(new Message(products, "Products retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error retrieving products", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> findById(Integer id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                log.info("Product with id {} retrieved successfully", id);
                return new ResponseEntity<>(new Message(product.get(), "Product found", TypesResponse.SUCCESS), HttpStatus.OK);
            } else {
                log.warn("Product with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error retrieving product with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error retrieving product", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> create(ProductDTO productDTO) {
        try {
            // Validar campos necesarios
            if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
                return new ResponseEntity<>(new Message(null, "Product name is required", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
            }

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStatus(productDTO.getStatus() != null ? productDTO.getStatus() : true);
            productRepository.save(product);

            log.info("Product created successfully: {}", product);
            return new ResponseEntity<>(new Message(product, "Product created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating product", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> update(Integer id, ProductDTO productDTO) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();

                // Actualizar campos
                if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
                    product.setName(productDTO.getName());
                }
                if (productDTO.getDescription() != null) {
                    product.setDescription(productDTO.getDescription());
                }
                if (productDTO.getPrice() != null && productDTO.getPrice() > 0) {
                    product.setPrice(productDTO.getPrice());
                }
                if (productDTO.getStatus() != null) {
                    product.setStatus(productDTO.getStatus());
                }

                productRepository.save(product);
                log.info("Product with id {} updated successfully", id);
                return new ResponseEntity<>(new Message(product, "Product updated", TypesResponse.SUCCESS), HttpStatus.OK);
            } else {
                log.warn("Product with id {} not found for update", id);
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error updating product with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating product", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                productRepository.delete(product.get());
                log.info("Product with id {} deleted successfully", id);
                return new ResponseEntity<>(new Message(null, "Product deleted", TypesResponse.SUCCESS), HttpStatus.OK);
            } else {
                log.warn("Product with id {} not found for deletion", id);
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error deleting product with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error deleting product", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
