package jbar.service_core.RatingUserSell.Model;

import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingUserSellRepository extends JpaRepository<RatingUserSell, Integer> {

    // Método para obtener los ratings por usuario
    List<RatingUserSell> findByUser_UserId(Integer userId);

    // Método para obtener los ratings por venta
    List<RatingUserSell> findBySell_SellId(Integer sellId);
}
