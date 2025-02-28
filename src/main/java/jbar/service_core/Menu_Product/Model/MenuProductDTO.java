package jbar.service_core.Menu_Product.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class MenuProductDTO {

    public interface Create {}
    public interface Update {}

    @Schema(description = "ID del menú", example = "1")
    @NotNull(groups = {Create.class, Update.class})
    private Integer menuId;

    @Schema(description = "Lista de IDs de productos", example = "[1, 2, 3]")
    @NotNull(groups = {Create.class, Update.class})
    private List<Integer> productIds;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
