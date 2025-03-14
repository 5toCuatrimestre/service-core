package jbar.service_core.Company.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // 🔹 Buscar la empresa con ID = 1 (Por defecto)
    Optional<Company> findByCompanyId(Integer companyId);
}
