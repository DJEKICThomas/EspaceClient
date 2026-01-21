package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.RequestDetail;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDetailRepository extends JpaRepository<RequestDetail, Long> {

    //FIXME  @Query(value="...,nativeQuery=true") for native query

    @Query("select rd from REQUEST_DETAIL as rd where rd.flag = ?1 and rd.typ <> ?2 ")
    List<RequestDetail> getListDetail(RequestFlag flag, RequestType typ);

    RequestDetail getRequestDetailById(long id);

    @Query("select rd from REQUEST_DETAIL as rd where rd.flag = ?1")
    List<RequestDetail> getListDetailAccepted(RequestFlag flag);

    @Query("select rd from REQUEST_DETAIL as rd where rd.requestHeader.user = ?1")
    List<RequestDetail> getUserListDetail(String userMail);


}

