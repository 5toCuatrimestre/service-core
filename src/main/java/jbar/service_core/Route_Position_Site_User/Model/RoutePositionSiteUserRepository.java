package jbar.service_core.Route_Position_Site_User.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePositionSiteUserRepository extends JpaRepository<RoutePositionSiteUser, Integer> {

    // 🔹 Buscar por Route ID
    List<RoutePositionSiteUser> findByRoute_RouteId(Integer routeId);

    // 🔹 Buscar por Position ID
    List<RoutePositionSiteUser> findByPosition_PositionId(Integer positionId);

    // 🔹 Buscar por Site ID
    List<RoutePositionSiteUser> findBySite_SiteId(Integer siteId);

    // 🔹 Buscar por User ID
    List<RoutePositionSiteUser> findByUser_UserId(Integer userId);

    // 🔹 Buscar solo los registros activos (Soft Delete)
    List<RoutePositionSiteUser> findByDeletedAtIsNull();
}
