package be.depairon.espaceclient.dao;


import be.depairon.espaceclient.entity.CusXdc;
import be.depairon.espaceclient.entity.RequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CusXdcDao extends JpaRepository<RequestDetail, Long> {

    @Query("select c from CusXdc c  where c.cusXdcNcs=?1 and c.cusXdcNda=?2")
    List<CusXdc> listCusXdc(String ncs, int nda);
}
