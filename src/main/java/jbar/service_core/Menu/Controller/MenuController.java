package jbar.service_core.Menu.Controller;

import jbar.service_core.Menu.Model.MenuDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllMenus() {
        return menuService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMenuById(@PathVariable Integer id) {
        return menuService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createMenu(@RequestBody MenuDTO menuDTO) {
        return menuService.create(menuDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMenu(@PathVariable Integer id, @RequestBody MenuDTO menuDTO) {
        return menuService.update(id, menuDTO);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeMenuStatus(@PathVariable Integer id) {
        return menuService.changeStatus(id);
    }
}
