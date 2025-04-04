package jbar.service_core.Route_Position_Site_User.Controller;

import jbar.service_core.Position_Site.Service.PositionSite;
import jbar.service_core.Position_Site.Service.PositionSiteRepository;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUser;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserDTO;
import jbar.service_core.Route_Position_Site_User.Model.RoutePositionSiteUserRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
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
public class RoutePositionSiteUserService {
    private final Logger log = LoggerFactory.getLogger(RoutePositionSiteUserService.class);
    private final RoutePositionSiteUserRepository repository;
    private final RouteRepository routeRepository;
    private final PositionSiteRepository positionSiteRepository;  // 🔹 Se usa PositionSiteRepository
    private final UserRepository userRepository;

    @Autowired
    public RoutePositionSiteUserService(
            RoutePositionSiteUserRepository repository,
            RouteRepository routeRepository,
            PositionSiteRepository positionSiteRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.routeRepository = routeRepository;
        this.positionSiteRepository = positionSiteRepository;
        this.userRepository = userRepository;
    }

    /**
     * 🔹 Obtener todas las relaciones activas (soft delete aplicado)
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<RoutePositionSiteUser> entities = repository.findByDeletedAtIsNull();
        log.info("All active RoutePositionSiteUsers retrieved successfully");
        return ResponseEntity.ok(new Message(entities, "Active RoutePositionSiteUsers retrieved", TypesResponse.SUCCESS));
    }

    /**
     * 🔹 Obtener una relación por ID
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<RoutePositionSiteUser> entity = repository.findById(id);
        if (entity.isPresent()) {
            log.info("RoutePositionSiteUser with id {} retrieved successfully", id);
            return ResponseEntity.ok(new Message(entity.get(), "RoutePositionSiteUser found", TypesResponse.SUCCESS));
        }
        log.warn("RoutePositionSiteUser with id {} not found", id);
        return new ResponseEntity<>(new Message(null, "RoutePositionSiteUser not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByPositionSiteId(Integer positionSiteId) {
        List<RoutePositionSiteUser> entities = repository.findByPositionSite_PositionSiteId(positionSiteId);
        log.info("Found {} RoutePositionSiteUsers for positionSiteId {}", entities.size(), positionSiteId);
        return ResponseEntity.ok(new Message(entities, "RoutePositionSiteUsers found", TypesResponse.SUCCESS));
    }

    /**
     * 🔹 Crear una nueva relación Route-PositionSite-User
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(RoutePositionSiteUserDTO dto) {
        try {
            // Verificar que la ruta existe
            Optional<Route> route = routeRepository.findById(dto.getRouteId());
            if (route.isEmpty()) {
                log.warn("Route with ID {} not found", dto.getRouteId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message(null, "Route not found", TypesResponse.ERROR));
            }

            // Verificar que el sitio de posición existe
            Optional<PositionSite> positionSite = positionSiteRepository.findById(dto.getPositionSiteId());
            if (positionSite.isEmpty()) {
                log.warn("PositionSite with ID {} not found", dto.getPositionSiteId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message(null, "PositionSite not found", TypesResponse.ERROR));
            }

            // Verificar que el usuario existe
            Optional<User> user = userRepository.findById(dto.getUserId());
            if (user.isEmpty()) {
                log.warn("User with ID {} not found", dto.getUserId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message(null, "User not found", TypesResponse.ERROR));
            }

            // Crear la entidad RoutePositionSiteUser
            RoutePositionSiteUser entity = new RoutePositionSiteUser();
            entity.setRoute(route.get());
            entity.setPositionSite(positionSite.get());
            entity.setUser(user.get());
            entity.setCreatedAt(LocalDateTime.now());

            // Guardar la entidad
            repository.save(entity);

            log.info("RoutePositionSiteUser created successfully: {}", entity);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Message(entity, "RoutePositionSiteUser created", TypesResponse.SUCCESS));

        } catch (Exception e) {
            log.error("Error creating RoutePositionSiteUser: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message(null, "Error creating RoutePositionSiteUser", TypesResponse.ERROR));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, RoutePositionSiteUserDTO dto) {
        Optional<RoutePositionSiteUser> existingEntity = repository.findById(id);
        if (existingEntity.isPresent()) {
            RoutePositionSiteUser entity = existingEntity.get();

            Optional<Route> route = routeRepository.findById(dto.getRouteId());
            Optional<PositionSite> positionSite = positionSiteRepository.findById(dto.getPositionSiteId());
            Optional<User> user = userRepository.findById(dto.getUserId());

            if (route.isEmpty() || positionSite.isEmpty() || user.isEmpty()) {
                log.warn("One or more entities not found for RoutePositionSiteUser update");
                return new ResponseEntity<>(new Message(null, "Invalid data provided", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            entity.setRoute(route.get());
            entity.setPositionSite(positionSite.get());
            entity.setUser(user.get());
            entity.setUpdatedAt(LocalDateTime.now());
            repository.saveAndFlush(entity);

            log.info("RoutePositionSiteUser with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(entity, "RoutePositionSiteUser updated", TypesResponse.SUCCESS));
        }

        log.warn("RoutePositionSiteUser with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "RoutePositionSiteUser not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }


    /**
     * 🔹 Eliminar (soft delete) una relación Route-PositionSite-User
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> delete(Integer id) {
        Optional<RoutePositionSiteUser> entity = repository.findById(id);
        if (entity.isPresent()) {
            RoutePositionSiteUser routePositionSiteUser = entity.get();
            routePositionSiteUser.setDeletedAt(LocalDateTime.now());
            repository.save(routePositionSiteUser);

            log.info("RoutePositionSiteUser with id {} soft deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "RoutePositionSiteUser deleted (soft delete)", TypesResponse.SUCCESS), HttpStatus.OK);
        }

        log.warn("RoutePositionSiteUser with id {} not found for deletion", id);
        return new ResponseEntity<>(new Message(null, "RoutePositionSiteUser not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }
}
