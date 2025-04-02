package jbar.service_core.Product.Controller;

import com.fasterxml.jackson.core.io.JsonEOFException;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaDTO;
import jbar.service_core.Multimedia.Model.MultimediaRepository;
import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductDTO;
import jbar.service_core.Product.Model.ProductRepository;
import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryRepository;
import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Category.Model.ProductCategoryRepository;
import jbar.service_core.Product_Multimedia.ProductMultimediaRepository;
import jbar.service_core.Product_Multimedia.ProductMultimedia;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MultimediaRepository multimediaRepository;
    private final ProductMultimediaRepository productMultimediaRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            CategoryRepository categoryRepository, MultimediaRepository multimediaRepository, ProductMultimediaRepository productMultimediaRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.multimediaRepository = multimediaRepository;
        this.productMultimediaRepository = productMultimediaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStatus(product.getStatus());

            // Mapear categorías asociadas
            List<ProductCategory> productCategories = productCategoryRepository.findByProduct_ProductId(product.getProductId());
            List<CategoryDTO> categoryDTOs = productCategories.stream().map(pc -> {
                Category category = pc.getCategory();
                CategoryDTO catDto = new CategoryDTO();
                catDto.setCategoryId(category.getCategoryId());
                catDto.setName(category.getName());
                catDto.setStatus(category.getStatus());
                catDto.setCreatedAt(category.getCreatedAt());
                catDto.setUpdatedAt(category.getUpdatedAt());
                catDto.setDeletedAt(category.getDeletedAt());
                return catDto;
            }).collect(Collectors.toList());
            dto.setProductCategories(categoryDTOs);

            // Mapear multimedia asociada
            List<ProductMultimedia> productMultimedia = productMultimediaRepository.findByProduct_ProductId(product.getProductId());
            List<MultimediaDTO> multimediaDTOs = productMultimedia.stream().map(pm -> {
                Multimedia media = pm.getMultimedia();
                MultimediaDTO mDto = new MultimediaDTO();
                mDto.setId(media.getMultimediaId());
                mDto.setUrl(media.getUrl());
                return mDto;
            }).collect(Collectors.toList());
            dto.setMultimedia(multimediaDTOs);

            productDTOs.add(dto);
        }

        log.info("All products retrieved successfully");
        return new ResponseEntity<>(new Message(productDTOs, "Products retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStatus(product.getStatus());

            // Mapear las categorías asociadas
            List<ProductCategory> productCategories = productCategoryRepository.findByProduct_ProductId(product.getProductId());
            List<CategoryDTO> categoryDTOs = productCategories.stream().map(pc -> {
                Category category = pc.getCategory();
                CategoryDTO catDto = new CategoryDTO();
                catDto.setCategoryId(category.getCategoryId());
                catDto.setName(category.getName());
                catDto.setStatus(category.getStatus());
                catDto.setCreatedAt(category.getCreatedAt());
                catDto.setUpdatedAt(category.getUpdatedAt());
                catDto.setDeletedAt(category.getDeletedAt());
                return catDto;
            }).collect(Collectors.toList());
            dto.setProductCategories(categoryDTOs);

            // Mapear la multimedia asociada
            List<ProductMultimedia> productMultimedia = productMultimediaRepository.findByProduct_ProductId(product.getProductId());
            List<MultimediaDTO> multimediaDTOs = productMultimedia.stream().map(pm -> {
                Multimedia media = pm.getMultimedia();
                MultimediaDTO mDto = new MultimediaDTO();
                mDto.setId(media.getMultimediaId());
                mDto.setUrl(media.getUrl());
                return mDto;
            }).collect(Collectors.toList());
            dto.setMultimedia(multimediaDTOs);

            log.info("Product with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(dto, "Product found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Product with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }


    @Transactional(rollbackFor = { SQLException.class, JsonEOFException.class })
    public ResponseEntity<Message> create(ProductDTO productDTO) {
        // 1. Crear el producto
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.getStatus() != null ? productDTO.getStatus() : true);

        // 2. Guardar el producto
        product = productRepository.saveAndFlush(product);
        final Product finalProduct = product;

        // 3. Asociar categorías si se proporcionan
        if (productDTO.getProductCategories() != null && !productDTO.getProductCategories().isEmpty()) {
            List<ProductCategory> productCategories = productDTO.getProductCategories().stream()
                    .map(category -> {
                        // Buscar la categoría por su ID
                        Category categoryEntity = categoryRepository.findById(category.getCategoryId())
                                .orElseThrow(() -> new RuntimeException(
                                        "Category not found with ID: " + category.getCategoryId()));

                        // Crear la relación ProductCategory
                        return new ProductCategory(finalProduct, categoryEntity);
                    })
                    .collect(Collectors.toList());

            // Guardar las relaciones en la tabla intermedia
            productCategoryRepository.saveAll(productCategories);
        }

        // 4. Asociar multimedia si se proporcionan
        if (productDTO.getMultimedia() != null && !productDTO.getMultimedia().isEmpty()) {
            // Validar que los IDs de multimedia existan
            for (MultimediaDTO multimediaDTO : productDTO.getMultimedia()) {
                if (multimediaDTO.getId() == null) {
                    return new ResponseEntity<>(
                            new Message("El ID de la multimedia es obligatorio.", TypesResponse.ERROR),
                            HttpStatus.BAD_REQUEST);
                }
                if (!multimediaRepository.existsById(multimediaDTO.getId())) {
                    return new ResponseEntity<>(
                            new Message("No se encontró la multimedia con ID " + multimediaDTO.getId(),
                                    TypesResponse.ERROR),
                            HttpStatus.NOT_FOUND);
                }
            }

            // Crear las relaciones ProductMultimedia
            List<ProductMultimedia> productMultimediaList = productDTO.getMultimedia().stream()
                    .map(multimediaDTO -> {
                        Multimedia multimediaEntity = multimediaRepository.findById(multimediaDTO.getId())
                                .orElseThrow(() -> new RuntimeException(
                                        "Multimedia not found with ID: " + multimediaDTO.getId()));

                        return new ProductMultimedia(
                                finalProduct,
                                multimediaEntity);
                    })
                    .collect(Collectors.toList());

            // Guardar las relaciones ProductMultimedia
            productMultimediaRepository.saveAllAndFlush(productMultimediaList);
        }

        log.info("Producto registrado con éxito");

        // 6. Obtener las categorías asociadas
        List<ProductCategory> productCategories = productCategoryRepository
                .findByProduct_ProductId(finalProduct.getProductId());

        List<Category> categories = productCategories.stream()
                .map(ProductCategory::getCategory)
                .collect(Collectors.toList());

        // Log para mostrar el producto y las categorías asociadas
        log.info("Producto creado exitosamente: {}", finalProduct);

        // Convertir las categorías a un formato más legible (solo id y nombre)
        List<CategoryDTO> categoryNames = categories.stream()
                .map(category -> {
                    CategoryDTO newCategory = new CategoryDTO();
                    newCategory.setCategoryId(category.getCategoryId());
                    newCategory.setName(category.getName());
                    return newCategory;
                })
                .collect(Collectors.toList());

        log.info("Categorías asociadas al producto: {}", categoryNames);

        // 7. Obtener la multimedia asociada
        List<ProductMultimedia> productMultimedia = productMultimediaRepository
                .findByProduct_ProductId(finalProduct.getProductId());

        List<Multimedia> multimedia = productMultimedia.stream()
                .map(ProductMultimedia::getMultimedia)
                .collect(Collectors.toList());
        // Convertir la multimedia a un formato más legible (solo id y url)
        List<MultimediaDTO> multimediaList = multimedia.stream()
                .map(media -> {
                    MultimediaDTO newMedia = new MultimediaDTO();
                    newMedia.setId(media.getMultimediaId());
                    newMedia.setUrl(media.getUrl());
                    return newMedia;
                })
                .collect(Collectors.toList());
        log.info("Multimedia asociada al producto: {}", multimediaList);
// Asignar el id del producto creado al DTO
        productDTO.setProductId(finalProduct.getProductId());
// Retornar el producto junto con las categorías y multimedia asociadas
        productDTO.setMultimedia(multimediaList);
        productDTO.setProductCategories(categoryNames);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(productDTO, "Producto creado con categorías", TypesResponse.SUCCESS));

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            // 1. Actualizar la información básica del producto
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStatus(productDTO.getStatus());
            product.setUpdatedAt(LocalDateTime.now());

            // 2. Borrar relaciones previas de categorías
            if (productDTO.getProductCategories() != null) {
                productCategoryRepository.deleteByProduct_ProductId(id);  // Borra las categorías actuales relacionadas
            }

            // 3. Agregar nuevas categorías si se proporcionan
            if (productDTO.getProductCategories() != null && !productDTO.getProductCategories().isEmpty()) {
                List<ProductCategory> productCategories = productDTO.getProductCategories().stream()
                        .map(category -> {
                            // Buscar la categoría por su ID
                            Category categoryEntity = categoryRepository.findById(category.getCategoryId())
                                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + category.getCategoryId()));

                            // Crear la relación ProductCategory
                            return new ProductCategory(product, categoryEntity);
                        })
                        .collect(Collectors.toList());

                // Guardar las relaciones en la tabla intermedia
                productCategoryRepository.saveAll(productCategories);
            }

            // 4. Borrar relaciones previas de multimedia
            if (productDTO.getMultimedia() != null) {
                productMultimediaRepository.deleteByProduct_ProductId(id);  // Borra las multimedia actuales relacionadas
            }

            // 5. Agregar nuevas multimedia si se proporcionan
            if (productDTO.getMultimedia() != null && !productDTO.getMultimedia().isEmpty()) {
                List<ProductMultimedia> productMultimediaList = productDTO.getMultimedia().stream()
                        .map(multimediaDTO -> {
                            Multimedia multimediaEntity = multimediaRepository.findById(multimediaDTO.getId())
                                    .orElseThrow(() -> new RuntimeException("Multimedia not found with ID: " + multimediaDTO.getId()));

                            return new ProductMultimedia(product, multimediaEntity);
                        })
                        .collect(Collectors.toList());

                // Guardar las relaciones ProductMultimedia
                productMultimediaRepository.saveAllAndFlush(productMultimediaList);
            }

            // Guardar el producto actualizado
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
            return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR),
                    HttpStatus.NOT_FOUND);
        }
    }
}