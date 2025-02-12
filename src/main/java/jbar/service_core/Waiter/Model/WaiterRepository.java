package jbar.service_core.Waiter.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
    Optional<Waiter> findByUserUserId(Integer userId);
}
