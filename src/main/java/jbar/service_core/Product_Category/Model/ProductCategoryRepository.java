package jbar.service_core.Product_Category.Model;

import jbar.service_core.Product.Model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    void deleteByProduct(Product product);
    List<ProductCategory> findByProduct_ProductId(Integer productId);
    List<ProductCategory> deleteByProduct_ProductId(Integer productId);
}