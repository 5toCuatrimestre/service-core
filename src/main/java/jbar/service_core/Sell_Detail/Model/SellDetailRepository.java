package jbar.service_core.Sell_Detail.Model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellDetailRepository extends JpaRepository<SellDetail, Integer> {
    @Query(value = """
    SELECT p.name AS name, SUM(sd.quantity) AS ventas
    FROM sell_detail sd
    JOIN product p ON sd.product_id = p.product_id
    JOIN sell s ON sd.sell_id = s.sell_id
    WHERE s.sell_date BETWEEN :startDate AND :endDate 
      AND s.status = true 
      AND sd.status = true
    GROUP BY p.name
    ORDER BY ventas DESC
    """, nativeQuery = true)
List<Object[]> findTopSellingProducts(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

}
