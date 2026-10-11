package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.Webprofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebprofileRepository extends JpaRepository<Webprofile, Long> {

//    @Query(value = "SELECT wp.*, wr.* FROM YGDEPAIRON.WEBROLE wr " +
//            " LEFT OUTER JOIN YGDEPAIRON.WEBPROFILEROLE wpr on wpr.WEBROLEID = wr.ID or wpr.WEBROLEID IS NULL " +
//            " LEFT OUTER JOIN YGDEPAIRON.WEBPROFILE wp on wp.ID = wpr.WEBPROFILEID or wpr.WEBPROFILEID IS NULL " +
//            " WHERE wp.NAME = ?1", nativeQuery = true)
    Optional<Webprofile> findByName(String name);
}
