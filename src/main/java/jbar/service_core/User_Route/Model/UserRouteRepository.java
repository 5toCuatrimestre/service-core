package jbar.service_core.User_Route.Model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRouteRepository extends JpaRepository<UserRoute, Integer> {
}
