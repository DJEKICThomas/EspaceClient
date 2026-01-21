package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    RequestHeader save(RequestHeader entity);
    RequestDetail save(RequestDetail entity);

//    RequestDetail save(RequestDetail requestDetail);
//    RequestCarrier save(RequestCarrier requestCarrier);
//    ImprovementRequest save(ImprovementRequest improvementRequest);

    List<RequestHeader> getRequestHeaderList();

    RequestHeader getById(long id);
     RequestDetail getRequestDetailById(long id);

//    List<RequestCarrier> getRequestCarrierList();

    List<RequestHeader> getImprovementRequestList();
    List<RequestDetail> getListDetail(RequestFlag flag, RequestType type);
    List<Object[]> getNativeListDetail(RequestFlag flag, RequestType type);
    List<Object[]> getNativeListDetail(RequestFlag flag);

    List<RequestDetail> getListDetailAccepted(RequestFlag flag);

    List<RequestDetail> getUserListDetail(String userMail);
    List<Object[]> getNativeUserListDetail(String userMail);
}
