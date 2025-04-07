package jbar.service_core.Route_Position_Site_User.Controller;

import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserDTO;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route-position-site-user")
public class RoutePositionSiteUserController {

    private final RoutePositionSiteUserService routePositionSiteUserService;
    private static final Logger log = LoggerFactory.getLogger(RoutePositionSiteUserController.class);

    @Autowired
    public RoutePositionSiteUserController(RoutePositionSiteUserService routePositionSiteUserService) {
        this.routePositionSiteUserService = routePositionSiteUserService;
    }

    /**
     * 🔹 Obtener todas las relaciones Route-Position-Site-User activas (soft delete aplicado)
     */
    @GetMapping("/all")
    public ResponseEntity<Message> getAllRoutePositionSiteUsers() {
        return routePositionSiteUserService.findAll();
    }

    /**
     * 🔹 Obtener una relación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getRoutePositionSiteUserById(@PathVariable Integer id) {
        return routePositionSiteUserService.findById(id);
    }

    /**
     * 🔹 Crear una nueva relación Route-Position-Site-User
     */
    @PostMapping
    public ResponseEntity<Message> createRoutePositionSiteUser(@RequestBody RoutePositionSiteUserDTO dto) {
        if (dto.getUserId() == null) {
            log.error("User ID is missing in request payload: {}", dto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(null, "User ID is required", TypesResponse.ERROR));
        }
        return routePositionSiteUserService.create(dto);
    }

    /**
     * 🔹 Actualizar una relación Route-Position-Site-User por ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateRoutePositionSiteUser(@PathVariable Integer id, @RequestBody RoutePositionSiteUserDTO dto) {
        return routePositionSiteUserService.update(id, dto);
    }

    /**
     * 🔹 Eliminar (soft delete) una relación Route-Position-Site-User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteRoutePositionSiteUser(@PathVariable Integer id) {
        return routePositionSiteUserService.delete(id);
    }
    @GetMapping("/mobile/waiter/{userId}")
    public List<PositionSite> getTablesForWaiter(@PathVariable Integer userId) {
        return routePositionSiteUserService.findAssignedTablesForWaiter(userId);
    }


}