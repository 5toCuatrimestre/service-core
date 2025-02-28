package jbar.service_core.Menu_Product.Controller;

import jakarta.validation.Valid;
import jbar.service_core.Menu_Product.Model.MenuProductDTO;
import jbar.service_core.Menu_Product.Controller.MenuProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-products")
public class MenuProductController {

    private final MenuProductService menuProductService;

    public MenuProductController(MenuProductService menuProductService) {
        this.menuProductService = menuProductService;
    }

    @PostMapping
    public ResponseEntity<String> createMenuProducts(@RequestBody @Valid MenuProductDTO dto) {
        menuProductService.createMenuProducts(dto);
        return ResponseEntity.ok("Relaciones de productos con el menú creadas correctamente.");
    }
    @PutMapping
    public ResponseEntity<String> updateMenuProducts(@RequestBody @Valid MenuProductDTO dto) {
        menuProductService.updateMenuProducts(dto);
        return ResponseEntity.ok("Relaciones de productos con el menú actualizadas correctamente.");
    }
}
