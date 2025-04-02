package jbar.service_core.Multimedia.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
    Optional<Multimedia> findByUrl(String url);
}

