package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.Statistic2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Statistic2Repository extends JpaRepository<Statistic2, Long> {
    // FIXME: 27-03-23 use artId and cusId instead
    List<Statistic2> findAllByCustomer_IdAndArticle_IdAndYear1(long cusId, long artId, int year1);
}
