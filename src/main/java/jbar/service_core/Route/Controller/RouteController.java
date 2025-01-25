package jbar.service_core.Route.Controller;

import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jbar.service_core.Route.Model.RouteDTO;


@RestController
@RequestMapping("/route")
public class RouteController {

    private final jbar.service_core.Route.Controller.RouteService  routeService;

    @Autowired
    public RouteController(jbar.service_core.Route.Controller.RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllRoutes() {
        return routeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getRouteById(@PathVariable Integer id) {
        return routeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createRoute(@RequestBody RouteDTO routeDTO) {
        return routeService.create(routeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateRoute(@PathVariable Integer id, @RequestBody RouteDTO routeDTO) {
        return routeService.update(id, routeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteRoute(@PathVariable Integer id) {
        return routeService.delete(id);
    }
}
