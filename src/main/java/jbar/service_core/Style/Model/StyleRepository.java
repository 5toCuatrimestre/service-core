package jbar.service_core.Style.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * Busca un estilo por su nombre (ignora mayúsculas y minúsculas).
     * @param name Nombre del estilo
     * @return Estilo con el nombre indicado
     */
    List<Style> findByNameContainingIgnoreCase(String name);
}
