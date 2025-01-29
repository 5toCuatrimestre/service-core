package jbar.service_core.User.Model;

import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findByRol(Rol rol);

    // ✅ Método corregido para obtener usuarios por sitio
    List<User> findBySite_SiteId(Integer siteId);
}
