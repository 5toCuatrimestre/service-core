package jbar.service_core.Category.Model;

import jbar.service_core.Util.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // 🔹 Buscar categoría por nombre exacto
    Optional<Category> findByName(String name);

    // 🔹 Buscar categoría ignorando mayúsculas/minúsculas
    Optional<Category> findByNameIgnoreCase(String name);

    // 🔹 Buscar categorías cuyo nombre contenga una palabra clave (Ignorando mayúsculas/minúsculas)
    List<Category> findByNameContainingIgnoreCase(String name);

    // 🔹 Buscar todas las categorías activas/inactivas
    List<Category> findByStatus(Status status);
}
