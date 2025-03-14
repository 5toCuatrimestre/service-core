package jbar.service_core.Site.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    /**
     * 🔹 Buscar todos los sitios que no han sido eliminados (Soft Delete),
     * incluyendo la empresa asociada y las posiciones dentro del sitio.
     */
    @Query("SELECT s FROM Site s JOIN FETCH s.company LEFT JOIN FETCH s.positionSites WHERE s.deletedAt IS NULL")
    List<Site> findByDeletedAtIsNull();

    /**
     * 🔹 Buscar un sitio por ID, incluyendo la empresa asociada y las posiciones dentro del sitio.
     */
    @Query("SELECT s FROM Site s JOIN FETCH s.company LEFT JOIN FETCH s.positionSites WHERE s.siteId = :id")
    Optional<Site> findByIdWithDetails(@Param("id") Integer id);
}
