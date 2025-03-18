package jbar.service_core.Category.Model;

import jbar.service_core.Util.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByNameIgnoreCase(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByStatus(Status status);

}

