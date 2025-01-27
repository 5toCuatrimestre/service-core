package jbar.service_core.Company_Style.Model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStyleRepository extends JpaRepository<CompanyStyle, Integer> {
}
