package jbar.service_core.Route.Controller;

import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteDTO;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Enum.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RouteService {
    private final Logger log = LoggerFactory.getLogger(RouteService.class);
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Route> routes = routeRepository.findAll();
        log.info("All routes retrieved successfully");
        return new ResponseEntity<>(new Message(routes, "Routes retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

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

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.getName());
        route.setDistance(routeDTO.getDistance());
        route.setEstimatedTime(routeDTO.getEstimatedTime());
        route.setDifficultyLevel(routeDTO.getDifficultyLevel());
        route.setStatus(Status.valueOf(routeDTO.getStatus().toUpperCase()));

        routeRepository.save(route);
        log.info("Route created successfully: {}", route);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(route, "Route created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, RouteDTO routeDTO) {
        Optional<Route> existingRoute = routeRepository.findById(id);
        if (existingRoute.isPresent()) {
            Route route = existingRoute.get();
            route.setName(routeDTO.getName());
            route.setDistance(routeDTO.getDistance());
            route.setEstimatedTime(routeDTO.getEstimatedTime());
            route.setDifficultyLevel(routeDTO.getDifficultyLevel());
            route.setStatus(Status.valueOf(routeDTO.getStatus().toUpperCase()));

            routeRepository.saveAndFlush(route);
            log.info("Route with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(route, "Route updated", TypesResponse.SUCCESS));
        }

        log.warn("Route with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            Route existingRoute = route.get();
            if (existingRoute.getStatus() == Status.ACTIVE) {
                existingRoute.setStatus(Status.INACTIVE);
                existingRoute.setDeletedAt(java.time.LocalDateTime.now());
            } else {
                existingRoute.setStatus(Status.ACTIVE);
                existingRoute.setDeletedAt(null);
            }
            routeRepository.save(existingRoute);
            log.info("Route with id {} status changed", id);
            return new ResponseEntity<>(new Message(existingRoute, "Route status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Route with id {} not found for status change", id);
            return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
