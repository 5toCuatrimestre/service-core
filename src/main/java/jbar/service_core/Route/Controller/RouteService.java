package jbar.service_core.Route.Controller;

import jbar.service_core.Company.Model.Company;
import jbar.service_core.Company.Model.CompanyDTO;
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

import java.time.LocalDateTime;
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
        route.setStatus(routeDTO.getStatus());

        routeRepository.save(route);
        log.info("Route created successfully: {}", route);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(route, "Route created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, RouteDTO routeDTO) {
        Optional<Route> existingRouteOptional = routeRepository.findById(id);
        if (existingRouteOptional.isPresent()) {
            Route route = existingRouteOptional.get();
            if (routeDTO.getName() != null) route.setName(routeDTO.getName());
            if (routeDTO.getDistance() != null) route.setDistance(routeDTO.getDistance());
            route.setUpdatedAt(LocalDateTime.now());

            routeRepository.saveAndFlush(route);
            return ResponseEntity.ok(new Message(route, "Route updated", TypesResponse.SUCCESS));
        }
        return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            Route existingRoute = route.get();
            existingRoute.setStatus(existingRoute.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            existingRoute.setDeletedAt(existingRoute.getStatus() == Status.INACTIVE ? LocalDateTime.now() : null);
            routeRepository.save(existingRoute);
            return new ResponseEntity<>(new Message(existingRoute, "Route status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

}
