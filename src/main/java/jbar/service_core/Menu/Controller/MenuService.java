package jbar.service_core.Menu.Controller;

import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu.Model.MenuDTO;
import jbar.service_core.Menu.Model.MenuRepository;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Menu_Product.Model.MenuProduct;
import jbar.service_core.Menu_Product.Model.MenuProductDTO;
import jbar.service_core.Menu_Product.Model.MenuProductRepository;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaDTO;
import jbar.service_core.Multimedia.Model.MultimediaRepository;
import jbar.service_core.Product.Controller.ProductService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final MenuRepository menuRepository;
    private final MenuProductRepository menuProductRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MultimediaRepository multimediaRepository;
    private final ProductMultimediaRepository productMultimediaRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, ProductRepository productRepository,
                       ProductCategoryRepository productCategoryRepository,
                       CategoryRepository categoryRepository, MultimediaRepository multimediaRepository,
                       ProductMultimediaRepository productMultimediaRepository, MenuProductRepository menuProductRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.multimediaRepository = multimediaRepository;
        this.productMultimediaRepository = productMultimediaRepository;
        this.menuProductRepository = menuProductRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return ResponseEntity.ok(new Message(menus, "Menus retrieved", TypesResponse.SUCCESS));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Menu> menuOpt = menuRepository.findById(id);
        return menuOpt.map(menu -> {
            // Crear DTO para el menú
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setName(menu.getName());
            menuDTO.setDescription(menu.getDescription());
            menuDTO.setStatus(menu.getStatus());
            menuDTO.setCreatedAt(menu.getCreatedAt());
            menuDTO.setUpdatedAt(menu.getUpdatedAt());

            // Obtener los productos relacionados con el menú
            List<MenuProduct> menuProducts = menuProductRepository.findByMenu_MenuId(menu.getMenuId());

            // Mapear los productos
            List<ProductDTO> productDTOs = menuProducts.stream().map(menuProduct -> {
                Product product = menuProduct.getProduct();
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductId(product.getProductId());
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setStatus(product.getStatus());

                // Mapear las categorías asociadas al producto
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
                productDTO.setProductCategories(categoryDTOs);

                // Mapear la multimedia asociada al producto
                List<ProductMultimedia> productMultimedia = productMultimediaRepository.findByProduct_ProductId(product.getProductId());
                List<MultimediaDTO> multimediaDTOs = productMultimedia.stream().map(pm -> {
                    Multimedia media = pm.getMultimedia();
                    MultimediaDTO mDto = new MultimediaDTO();
                    mDto.setId(media.getMultimediaId());
                    mDto.setUrl(media.getUrl());
                    return mDto;
                }).collect(Collectors.toList());
                productDTO.setMultimedia(multimediaDTOs);

                return productDTO;
            }).collect(Collectors.toList());

            // Asignar la lista de productos al DTO del menú
            menuDTO.setProducts(productDTOs);

            log.info("Menu with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(menuDTO, "Menu found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Menu with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Menu not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(MenuDTO menuDTO) {
        Menu menu = new Menu(menuDTO.getName(), menuDTO.getDescription(), menuDTO.getStatus());
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(menu, "Menu created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, MenuDTO menuDTO) {
        return menuRepository.findById(id).map(menu -> {
            // Actualizamos la fecha de modificación del menú
            menu.setUpdatedAt(LocalDateTime.now());
            menuRepository.save(menu);

            // 1. Eliminar todas las relaciones anteriores del menú con productos
            List<MenuProduct> existingRelations = menuProductRepository.findByMenu_MenuId(menu.getMenuId());
            menuProductRepository.deleteAll(existingRelations);

            // 2. Crear nuevas relaciones con los productos dados (solo productIds)
            List<Integer> newProductIds = menuDTO.getProductIds();  // Aquí obtenemos los productIds
            if (newProductIds != null) {
                for (Integer productId : newProductIds) {
                    Optional<Product> productOpt = productRepository.findById(productId);
                    if (productOpt.isPresent()) {
                        // Verificamos si la relación ya existe
                        Optional<MenuProduct> existingMenuProduct = menuProductRepository
                                .findByMenu_MenuIdAndProduct_ProductId(menu.getMenuId(), productId);

                        if (!existingMenuProduct.isPresent()) {
                            // Si no existe, creamos la nueva relación
                            MenuProduct newRelation = new MenuProduct();
                            newRelation.setMenu(menu);
                            newRelation.setProduct(productOpt.get());
                            menuProductRepository.save(newRelation);
                        }
                    }
                }
            }

            return ResponseEntity.ok(new Message(menu, "Menu updated with new products", TypesResponse.SUCCESS));
        }).orElseGet(() ->
                new ResponseEntity<>(new Message(null, "Menu not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
        );
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        return menuRepository.findById(id).map(menu -> {
            menu.setStatus(!menu.getStatus());
            menu.setUpdatedAt(LocalDateTime.now());
            menuRepository.save(menu);
            return ResponseEntity.ok(new Message(null, "Menu status changed", TypesResponse.SUCCESS));
        }).orElseGet(() ->
                new ResponseEntity<>(new Message(null, "Menu not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
        );
    }
}
