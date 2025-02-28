package jbar.service_core.Menu_Product.Controller;

import jbar.service_core.Menu_Product.Model.MenuProductDTO;
import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu_Product.Model.MenuProduct;
import jbar.service_core.Menu_Product.Model.MenuProductRepository;
import jbar.service_core.Menu.Model.MenuRepository;
import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuProductService {

    private final MenuProductRepository menuProductRepository;
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public MenuProductService(MenuProductRepository menuProductRepository, MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuProductRepository = menuProductRepository;
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void createMenuProducts(MenuProductDTO dto) {
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Menú no encontrado"));

        List<Product> products = productRepository.findAllById(dto.getProductIds());

        if (products.size() != dto.getProductIds().size()) {
            throw new IllegalArgumentException("Uno o más productos no existen");
        }

        List<MenuProduct> menuProducts = products.stream()
                .map(product -> new MenuProduct(menu, product))
                .collect(Collectors.toList());

        menuProductRepository.saveAll(menuProducts);
    }

    @Transactional
    public void updateMenuProducts(MenuProductDTO dto) {
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Menú no encontrado"));

        menuProductRepository.deleteByMenu(menu);

        List<Product> products = productRepository.findAllById(dto.getProductIds());

        if (products.size() != dto.getProductIds().size()) {
            throw new IllegalArgumentException("Uno o más productos no existen");
        }

        List<MenuProduct> menuProducts = products.stream()
                .map(product -> new MenuProduct(menu, product))
                .collect(Collectors.toList());

        menuProductRepository.saveAll(menuProducts);
    }
}
