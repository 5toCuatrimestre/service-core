package jbar.service_core.Position_Site.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionSiteRepository extends JpaRepository<PositionSite, Integer> {
}
