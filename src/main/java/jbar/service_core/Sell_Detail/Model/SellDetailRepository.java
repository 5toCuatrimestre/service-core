package jbar.service_core.Sell_Detail.Model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellDetailRepository extends JpaRepository<SellDetail, Integer> {
    @Query("SELECT p.name as productName, SUM(sd.quantity) as totalQuantity " +
           "FROM SellDetail sd " +
           "JOIN sd.product p " +
           "WHERE sd.sell.sellDate BETWEEN :startDate AND :endDate " +
           "AND sd.deletedAt IS NULL " +
           "GROUP BY p.name " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    List<SellDetail> findBySell_SellIdAndDeletedAtIsNull(Integer sellId);
}
