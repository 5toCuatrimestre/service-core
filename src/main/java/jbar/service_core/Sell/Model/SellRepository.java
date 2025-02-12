package jbar.service_core.Sell.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer> {

    List<Sell> findByUser_UserId(Integer userId);

    List<Sell> findByWaiter_WaiterId(Integer waiterId);

    List<Sell> findBySellDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Sell> findByStatusTrue();  // Ventas activas

    List<Sell> findByStatusFalse(); // Ventas inactivas
}
