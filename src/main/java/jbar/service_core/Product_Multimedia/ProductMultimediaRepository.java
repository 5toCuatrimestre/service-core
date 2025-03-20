package jbar.service_core.Product_Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMultimediaRepository extends JpaRepository<ProductMultimedia, Integer> {

    List<ProductMultimedia> findByProduct_ProductId(Integer productId);
}
