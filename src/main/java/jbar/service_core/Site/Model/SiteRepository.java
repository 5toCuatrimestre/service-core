package jbar.service_core.Site.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    /**
     * 🔹 Buscar todos los sitios que no han sido eliminados (Soft Delete)
     */
    List<Site> findByDeletedAtIsNull();
}
