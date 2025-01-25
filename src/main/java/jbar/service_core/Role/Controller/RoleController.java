package jbar.service_core.Role.Controller;

import jbar.service_core.Role.Model.RoleDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final jbar.service_core.Role.Controller.RoleService roleService;

    @Autowired
    public RoleController(jbar.service_core.Role.Controller.RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getRoleById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.create(roleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        return roleService.update(id, roleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteRole(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
