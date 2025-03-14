package jbar.service_core.Route.Controller;

import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Position_Site.Service.PositionSiteRepository;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteDTO;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserRepository;
import jbar.service_core.Util.Enum.Status;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
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

@Service
@Transactional
public class RouteService {
    private final Logger log = LoggerFactory.getLogger(RouteService.class);
    private final RouteRepository routeRepository;
    private final PositionSiteRepository positionSiteRepository;
    private final RoutePositionSiteUserRepository routePositionSiteUserRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        PositionSiteRepository positionSiteRepository,
                        RoutePositionSiteUserRepository routePositionSiteUserRepository) {
        this.routeRepository = routeRepository;
        this.positionSiteRepository = positionSiteRepository;
        this.routePositionSiteUserRepository = routePositionSiteUserRepository;
    }

    /**
     * 🔹 Obtener todas las rutas activas (Soft Delete)
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Route> routes = routeRepository.findByDeletedAtIsNull();
        log.info("All active routes retrieved successfully");
        return new ResponseEntity<>(new Message(routes, "Active routes retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    /**
     * 🔹 Obtener una ruta por ID
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        return route.map(value -> {
            log.info("Route with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Route found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Route with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    /**
     * 🔹 Crear una nueva ruta y asignar una posición por defecto
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.getName());
        route.setStatus(routeDTO.getStatus()); // No se usa toUpperCase()

        // 🔹 Guardar la ruta primero
        routeRepository.save(route);
        log.info("Route created successfully: {}", route);

        // 🔹 Obtener una posición por defecto
        Optional<PositionSite> defaultPositionSite = positionSiteRepository.findById(1);
        if (defaultPositionSite.isPresent()) {
            PositionSite positionSite = defaultPositionSite.get();
            RoutePositionSiteUser routePositionSiteUser = new RoutePositionSiteUser();
            routePositionSiteUser.setRoute(route);
            routePositionSiteUser.setPositionSite(positionSite);

            routePositionSiteUserRepository.save(routePositionSiteUser);
            log.info("Default PositionSite assigned to route: {}", positionSite.getPositionSiteId());
        } else {
            log.warn("No default PositionSite found to assign.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(route, "Route created", TypesResponse.SUCCESS));
    }

    /**
     * 🔹 Actualizar una ruta existente
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, RouteDTO routeDTO) {
        Optional<Route> existingRouteOptional = routeRepository.findById(id);
        if (existingRouteOptional.isPresent()) {
            Route route = existingRouteOptional.get();

            if (routeDTO.getName() != null) route.setName(routeDTO.getName());
            route.setUpdatedAt(LocalDateTime.now());

            routeRepository.saveAndFlush(route);
            log.info("Route with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(route, "Route updated", TypesResponse.SUCCESS));
        }

        log.warn("Route with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    /**
     * 🔹 Cambiar el estado de una ruta (Soft Delete)
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (routeOptional.isPresent()) {
            Route route = routeOptional.get();
            route.setStatus(route.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            route.setDeletedAt(route.getStatus() == Status.INACTIVE ? LocalDateTime.now() : null);

            routeRepository.save(route);
            log.info("Route with id {} status changed", id);
            return new ResponseEntity<>(new Message(route, "Route status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        }

        log.warn("Route with id {} not found for status change", id);
        return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }
}
