package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.dao.ScmAllDocDao;
import be.depairon.espaceclient.entity.ScmAllDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScmAllDocRepository extends JpaRepository<ScmAllDoc, String> {
}
