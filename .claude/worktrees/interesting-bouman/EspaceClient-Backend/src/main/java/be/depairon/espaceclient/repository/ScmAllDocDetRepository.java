package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.ScmAllDocDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScmAllDocDetRepository extends JpaRepository<ScmAllDocDet, Long> {
}