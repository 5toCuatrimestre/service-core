package jbar.service_core.Route_Position_Site_User.Controller;

import jbar.service_core.Position.Model.PositionRepository;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserDTO;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserRepository;
import jbar.service_core.Site.Model.SiteRepository;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Position.Model.Position;

import jbar.service_core.User.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoutePositionSiteUserService {
    private final Logger log = LoggerFactory.getLogger(RoutePositionSiteUserService.class);
    private final RoutePositionSiteUserRepository repository;
    private final RouteRepository routeRepository;
    private final PositionRepository positionRepository;
    private final SiteRepository siteRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoutePositionSiteUserService(
            RoutePositionSiteUserRepository repository,
            RouteRepository routeRepository,
            PositionRepository positionRepository,
            SiteRepository siteRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.routeRepository = routeRepository;
        this.positionRepository = positionRepository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Message> create(RoutePositionSiteUserDTO dto) {
        try {
            Optional<Route> route = routeRepository.findById(dto.getRouteId());
            if (route.isEmpty()) {
                log.warn("Route with id {} not found", dto.getRouteId());
                return new ResponseEntity<>(new Message(null, "Route not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<Position> position = positionRepository.findById(dto.getPositionId());
            if (position.isEmpty()) {
                log.warn("Position with id {} not found", dto.getPositionId());
                return new ResponseEntity<>(new Message(null, "Position not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<Site> site = siteRepository.findById(dto.getSiteId());
            if (site.isEmpty()) {
                log.warn("Site with id {} not found", dto.getSiteId());
                return new ResponseEntity<>(new Message(null, "Site not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<User> user = userRepository.findById(dto.getUserId());
            if (user.isEmpty()) {
                log.warn("User with id {} not found", dto.getUserId());
                return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            RoutePositionSiteUser entity = new RoutePositionSiteUser();
            entity.setRoute(route.get());
            entity.setPosition(position.get());
            entity.setSite(site.get());
            entity.setUser(user.get());
            repository.save(entity);

            log.info("RoutePositionSiteUser created successfully: {}", entity);
            return new ResponseEntity<>(new Message(entity, "RoutePositionSiteUser created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating RoutePositionSiteUser: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating RoutePositionSiteUser", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
