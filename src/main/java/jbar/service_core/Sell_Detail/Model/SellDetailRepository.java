package jbar.service_core.Sell_Detail.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellDetailRepository extends JpaRepository<SellDetail, Integer> {
}
