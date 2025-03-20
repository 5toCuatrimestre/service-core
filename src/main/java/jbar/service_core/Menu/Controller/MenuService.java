package jbar.service_core.Menu.Controller;

import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu.Model.MenuDTO;
import jbar.service_core.Menu.Model.MenuRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
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
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return ResponseEntity.ok(new Message(menus, "Menus retrieved", TypesResponse.SUCCESS));
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(value ->
                ResponseEntity.ok(new Message(value, "Menu found", TypesResponse.SUCCESS))
        ).orElseGet(() ->
                new ResponseEntity<>(new Message(null, "Menu not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
        );
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
            menu.setName(menuDTO.getName());
            menu.setDescription(menuDTO.getDescription());
            menu.setStatus(menuDTO.getStatus());
            menu.setUpdatedAt(LocalDateTime.now());
            menuRepository.save(menu);
            return ResponseEntity.ok(new Message(menu, "Menu updated", TypesResponse.SUCCESS));
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
