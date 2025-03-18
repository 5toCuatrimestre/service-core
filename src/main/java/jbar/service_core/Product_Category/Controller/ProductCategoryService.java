package jbar.service_core.Product_Category.Controller;

import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Category.Model.ProductCategoryDTO;
import jbar.service_core.Product_Category.Model.ProductCategoryRepository;
import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryRepository;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                  ProductRepository productRepository,
                                  CategoryRepository categoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProductCategory> findById(Integer id) {
        return productCategoryRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductCategory create(ProductCategoryDTO productCategoryDTO) {
        // Buscar el producto y la categoría por sus IDs
        Product product = productRepository.findById(productCategoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(productCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Crear la relación ProductCategory
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(product);
        productCategory.setCategory(category);

        // Guardar la relación
        return productCategoryRepository.save(productCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductCategory update(Integer id, ProductCategoryDTO productCategoryDTO) {
        Optional<ProductCategory> existingProductCategory = productCategoryRepository.findById(id);
        if (existingProductCategory.isPresent()) {
            ProductCategory updatedProductCategory = existingProductCategory.get();

            // Buscar el producto y la categoría por sus IDs
            Product product = productRepository.findById(productCategoryDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            Category category = categoryRepository.findById(productCategoryDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Actualizar la relación
            updatedProductCategory.setProduct(product);
            updatedProductCategory.setCategory(category);

            // Guardar la relación actualizada
            return productCategoryRepository.save(updatedProductCategory);
        } else {
            throw new RuntimeException("Product category not found");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        if (productCategory.isPresent()) {
            productCategoryRepository.delete(productCategory.get());
        } else {
            throw new RuntimeException("Product category not found");
        }
    }
}