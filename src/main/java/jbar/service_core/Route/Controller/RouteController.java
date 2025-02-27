package jbar.service_core.Route.Controller;

import jbar.service_core.Route.Model.RouteDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/route")
@Validated
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * 🔹 Obtener todas las rutas disponibles
     */
    @GetMapping("/all")
    public ResponseEntity<Message> getAllRoutes() {
        return routeService.findAll();
    }

    /**
     * 🔹 Obtener una ruta por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getRouteById(@PathVariable Integer id) {
        return routeService.findById(id);
    }

    /**
     * 🔹 Crear una nueva ruta
     */
    @PostMapping
    public ResponseEntity<Message> createRoute(@Valid @RequestBody RouteDTO routeDTO) {
        return routeService.create(routeDTO);
    }

    /**
     * 🔹 Actualizar una ruta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateRoute(@PathVariable Integer id, @Valid @RequestBody RouteDTO routeDTO) {
        return routeService.update(id, routeDTO);
    }

    /**
     * 🔹 Cambiar el estado de una ruta (activo/inactivo)
     */
    @PatchMapping("/status/{id}")
    public ResponseEntity<Message> changeRouteStatus(@PathVariable Integer id) {
        return routeService.changeStatus(id);
    }
}
