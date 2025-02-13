package jbar.service_core.Sell.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer> {

    /**
     * Find all sales made by a specific user.
     *
     * @param userId ID of the user
     * @return List of sales associated with the user
     */
    List<Sell> findByUserUserId(Integer userId);

    /**
     * Find all sales within a date range.
     *
     * @param startDate Start date (inclusive)
     * @param endDate   End date (inclusive)
     * @return List of sales within the given date range
     */
    List<Sell> findBySellDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieve only active sales.
     *
     * @return List of sales where status is true (active)
     */
    List<Sell> findByStatusTrue();

    /**
     * Retrieve only inactive (deleted) sales.
     *
     * @return List of sales where status is false (inactive)
     */
    List<Sell> findByStatusFalse();
}
