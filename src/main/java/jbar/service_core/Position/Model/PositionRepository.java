package jbar.service_core.Position.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    /**
     * 🔹 Buscar posiciones por nombre (insensible a mayúsculas y minúsculas).
     */
    List<Position> findByNameContainingIgnoreCase(String name);

    /**
     * 🔹 Buscar todas las posiciones activas (que no han sido eliminadas - Soft Delete).
     */
    List<Position> findByDeletedAtIsNull();

    /**
     * 🔹 Buscar una posición por ID, asegurando que no esté eliminada (Soft Delete).
     */
    Optional<Position> findByPositionIdAndDeletedAtIsNull(Integer positionId);

    /**
     * 🔹 Obtener todas las posiciones junto con los `PositionSites` asociados.
     * Evita el problema de `LazyInitializationException`.
     */
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.positionSites WHERE p.deletedAt IS NULL")
    List<Position> findAllWithPositionSites();

    /**
     * 🔹 Buscar una posición por ID e incluir los `PositionSites` relacionados.
     */
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.positionSites WHERE p.positionId = :positionId AND p.deletedAt IS NULL")
    Optional<Position> findByIdWithDetails(@Param("positionId") Integer positionId);
}
