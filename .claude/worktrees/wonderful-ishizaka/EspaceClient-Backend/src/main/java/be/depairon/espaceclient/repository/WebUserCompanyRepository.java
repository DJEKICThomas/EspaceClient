package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.WebUserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserCompanyRepository extends JpaRepository<WebUserCompany, Long> {
}
