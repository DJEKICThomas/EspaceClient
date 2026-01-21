package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.RequestHeader;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RequestHeaderDao {
    List<RequestHeader> getList(List<RequestType> requestTypeList);
    List<Object[]> getNativeListDetail(RequestFlag flag, RequestType type);
    List<Object[]> getNativeListDetail(RequestFlag flag);
    List<Object[]> getNativeUserListDetail(String userMail);
}
