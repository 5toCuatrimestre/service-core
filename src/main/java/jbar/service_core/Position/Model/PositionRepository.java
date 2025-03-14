package jbar.service_core.Position.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    // Buscar posiciones por nombre (insensible a mayúsculas y minúsculas)
    List<Position> findByNameContainingIgnoreCase(String name);

    // Buscar posiciones activas (soft delete)
    List<Position> findByDeletedAtIsNull();

}
