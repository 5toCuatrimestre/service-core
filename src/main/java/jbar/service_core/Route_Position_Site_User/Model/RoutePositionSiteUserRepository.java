package jbar.service_core.Route_Position_Site_User.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePositionSiteUserRepository extends JpaRepository<RoutePositionSiteUser, Integer> {
}
