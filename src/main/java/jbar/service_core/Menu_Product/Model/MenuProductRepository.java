package jbar.service_core.Menu_Product.Model;

import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu_Product.Model.MenuProduct;
import jbar.service_core.Product_Multimedia.ProductMultimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuProductRepository extends JpaRepository<MenuProduct, Integer> {

    List<MenuProduct> findByMenu(Menu menu);
    List<MenuProduct> findByMenu_MenuId(Integer productId);

    @Transactional
    void deleteByMenu(Menu menu);

    Optional<MenuProduct> findByMenu_MenuIdAndProduct_ProductId(Integer menuId, Integer productId);
}
