package jbar.service_core.Route_Position_Site_User.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePositionSiteUserRepository extends JpaRepository<RoutePositionSiteUser, Integer> {

    // 🔹 Buscar por Route ID
    List<RoutePositionSiteUser> findByRoute_RouteId(Integer routeId);

    // 🔹 Buscar por PositionSite ID (Corrección)
    List<RoutePositionSiteUser> findByPositionSite_PositionSiteId(Integer positionSiteId);

    // 🔹 Buscar por User ID
    List<RoutePositionSiteUser> findByUser_UserId(Integer userId);

    // 🔹 Buscar por User ID y que no esté eliminado
    List<RoutePositionSiteUser> findByUser_UserIdAndDeletedAtIsNull(Integer userId);

    // 🔹 Buscar solo los registros activos (Soft Delete)
    List<RoutePositionSiteUser> findByDeletedAtIsNull();
}