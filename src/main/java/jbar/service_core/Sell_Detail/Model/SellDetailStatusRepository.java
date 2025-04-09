package jbar.service_core.Sell_Detail.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellDetailStatusRepository extends JpaRepository<SellDetailStatus, Integer> {
    List<SellDetailStatus> findByStatusAndDeletedAtIsNull(SellDetailStatus.Status status);
    List<SellDetailStatus> findBySellDetailIdAndDeletedAtIsNull(Integer sellDetailId);
} 