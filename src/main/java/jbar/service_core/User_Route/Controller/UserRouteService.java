package jbar.service_core.User_Route.Controller;

import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.User_Route.Model.UserRoute;
import jbar.service_core.User_Route.Model.UserRouteDTO;

import jbar.service_core.User_Route.Model.UserRouteRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRouteService {
    private final Logger log = LoggerFactory.getLogger(UserRouteService.class);
    private final UserRouteRepository userRouteRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    @Autowired
    public UserRouteService(UserRouteRepository userRouteRepository, UserRepository userRepository, RouteRepository routeRepository) {
        this.userRouteRepository = userRouteRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    public ResponseEntity<Message> create(UserRouteDTO userRouteDTO) {
        try {
            Optional<User> user = userRepository.findById(userRouteDTO.getUserId());
            if (user.isEmpty()) {
                log.warn("User with id {} not found", userRouteDTO.getUserId());
                return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<Route> route = routeRepository.findById(userRouteDTO.getRouteId());
            if (route.isEmpty()) {
                log.warn("Route with id {} not found", userRouteDTO.getRouteId());
                return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            UserRoute userRoute = new UserRoute();
            userRoute.setUser(user.get());
            userRoute.setRoute(route.get());
            userRoute.setNotes(userRouteDTO.getNotes());
            userRouteRepository.save(userRoute);

            log.info("UserRoute created successfully: {}", userRoute);
            return new ResponseEntity<>(new Message(userRoute, "UserRoute created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating UserRoute: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating UserRoute", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
