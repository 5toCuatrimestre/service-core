package jbar.service_core.Route.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    // 🔹 Buscar solo rutas activas (Soft Delete)
    List<Route> findByDeletedAtIsNull();
}
