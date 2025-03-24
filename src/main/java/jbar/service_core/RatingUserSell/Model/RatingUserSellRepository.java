package jbar.service_core.RatingUserSell.Model;

import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RatingUserSellRepository extends JpaRepository<RatingUserSell, Integer> {

    // Método para obtener los ratings por usuario
    List<RatingUserSell> findByUser_UserId(Integer userId);

    // Método para obtener los ratings por venta
    List<RatingUserSell> findBySell_SellId(Integer sellId);

    @Query(value = """
        SELECT u.name AS waiter_name, SUM(r.stars) AS total_stars
        FROM rating_user_sell r
        JOIN user u ON r.user_id = u.user_id
        WHERE r.deleted_at IS NULL
          AND r.created_at BETWEEN :startDate AND :endDate
        GROUP BY u.name
        ORDER BY total_stars DESC
        """, nativeQuery = true)
    List<Object[]> findTotalStarsGroupedByWaiter(
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
    

}
