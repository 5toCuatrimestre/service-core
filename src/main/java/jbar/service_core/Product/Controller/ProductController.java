package jbar.service_core.Product.Controller;

import jbar.service_core.Product.Model.ProductDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getProductById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteProduct(@PathVariable Integer id) {
        return productService.delete(id);
    }
}
