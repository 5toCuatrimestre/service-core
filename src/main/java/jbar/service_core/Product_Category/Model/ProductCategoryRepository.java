package jbar.service_core.Product_Category.Model;

import jbar.service_core.Product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    void deleteByProduct(Product product);
}