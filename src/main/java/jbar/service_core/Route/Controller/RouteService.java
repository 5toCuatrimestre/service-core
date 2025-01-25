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
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final Logger log = LoggerFactory.getLogger(RouteService.class);
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Route> routes = routeRepository.findAll();
        log.info("All routes retrieved successfully");
        return new ResponseEntity<>(new Message(routes, "Routes retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            log.info("Route with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(route.get(), "Route found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Route with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.getName());
        route.setDistance(routeDTO.getDistance());
        route.setEstimatedTime(routeDTO.getEstimatedTime());
        route.setDifficultyLevel(routeDTO.getDifficultyLevel());
        route.setStatus(Status.valueOf(routeDTO.getStatus().toUpperCase()));
        routeRepository.save(route);
        log.info("Route created successfully: {}", route);
        return new ResponseEntity<>(new Message(route, "Route created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, RouteDTO routeDTO) {
        Optional<Route> existingRoute = routeRepository.findById(id);
        if (existingRoute.isPresent()) {
            Route route = existingRoute.get();
            route.setName(routeDTO.getName());
            route.setDistance(routeDTO.getDistance());
            route.setEstimatedTime(routeDTO.getEstimatedTime());
            route.setDifficultyLevel(routeDTO.getDifficultyLevel());
            route.setStatus(Status.valueOf(routeDTO.getStatus().toUpperCase()));
            routeRepository.save(route);
            log.info("Route with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(route, "Route updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Route with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            routeRepository.delete(route.get());
            log.info("Route with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Route deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Route with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
