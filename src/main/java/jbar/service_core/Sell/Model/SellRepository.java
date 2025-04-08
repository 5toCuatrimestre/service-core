package jbar.service_core.Sell.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer> {

    List<Sell> findByUserUserId(Integer userId);
    
    //delete null
    List<Sell> findByUserUserIdAndDeletedAtIsNull(Integer userId);

    List<Sell> findByPositionSiteIdAndDeletedAtIsNull(Integer positionSiteId);

    List<Sell> findBySellDateBetween(Timestamp startDate, Timestamp endDate);

    List<Sell> findByStatusTrue();

    List<Sell> findByStatusFalse();
}
