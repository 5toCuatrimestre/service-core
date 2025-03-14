package jbar.service_core.Style.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {

    /**
     * Encuentra todos los estilos activos.
     * @return Lista de estilos con status `true`
     */
    List<Style> findByStatusTrue();

    /**
     * Encuentra todos los estilos inactivos.
     * @return Lista de estilos con status `false`
     */
    List<Style> findByStatusFalse();



    /**
     * 🔹 Desactiva todos los estilos excepto el que se está activando.
     * @param id ID del estilo que debe permanecer activo.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Style s SET s.status = false WHERE s.styleId <> :id")
    void deactivateAllStylesExcept(Integer id);
}
