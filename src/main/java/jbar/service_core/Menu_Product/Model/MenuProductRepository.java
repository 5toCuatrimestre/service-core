package jbar.service_core.Menu_Product.Model;

import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu_Product.Model.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MenuProductRepository extends JpaRepository<MenuProduct, Integer> {

    List<MenuProduct> findByMenu(Menu menu);

    @Transactional
    void deleteByMenu(Menu menu);
}
