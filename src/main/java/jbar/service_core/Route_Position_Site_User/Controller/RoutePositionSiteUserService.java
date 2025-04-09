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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByUserId(Integer userId) {
        List<RoutePositionSiteUser> entities = repository.findByUser_UserId(userId);
        log.info("Found {} RoutePositionSiteUsers for userId {}", entities.size(), userId);
        return ResponseEntity.ok(new Message(entities, "RoutePositionSiteUsers found", TypesResponse.SUCCESS));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAssignedPositionsForWaiter(Integer userId) {
        log.info("Buscando posiciones para el usuario ID: {}", userId);
        
        List<RoutePositionSiteUser> assignments = repository.findByUser_UserIdAndDeletedAtIsNull(userId);
        log.info("Asignaciones encontradas: {}", assignments.size());
        
        List<Map<String, Object>> positionsData = new ArrayList<>();
        
        for (RoutePositionSiteUser assignment : assignments) {
            log.info("Procesando asignación ID: {}", assignment.getId());
            log.info("PositionSite ID de la asignación: {}", assignment.getPositionSite().getPositionSiteId());
            
            Optional<PositionSite> positionSite = positionSiteRepository.findById(assignment.getPositionSite().getPositionSiteId());
            if (positionSite.isPresent()) {
                log.info("PositionSite encontrado: {}", positionSite.get());
                if (positionSite.get().getDeletedAt() == null) {
                    log.info("PositionSite válido, agregando a la lista");
                    PositionSite ps = positionSite.get();
                    Map<String, Object> positionData = new HashMap<>();
                    positionData.put("position_siteId", ps.getPositionSiteId());
                    positionData.put("positionId", ps.getPosition().getPositionId());
                    positionData.put("siteId", ps.getSite().getSiteId());
                    positionData.put("capacity", ps.getCapacity());
                    positionData.put("xLocation", ps.getXLocation());
                    positionData.put("yLocation", ps.getYLocation());
                    positionData.put("status", ps.getStatus());
                    positionsData.add(positionData);
                } else {
                    log.info("PositionSite eliminado, saltando");
                }
            } else {
                log.warn("PositionSite no encontrado para ID: {}", assignment.getPositionSite().getPositionSiteId());
            }
        }
        
        log.info("Total de posiciones encontradas: {}", positionsData.size());
        return ResponseEntity.ok(new Message(positionsData, "Posiciones asignadas encontradas", TypesResponse.SUCCESS));
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

            // Actualizar las relaciones
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
