package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.*;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import be.depairon.espaceclient.service.RequestService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestHeaderRepository requestHeaderRepository;
    private final RequestDetailRepository requestDetailRepository;
    private final RequestHeaderDao requestHeaderDao;

    @Autowired
    public RequestServiceImpl(RequestHeaderRepository requestHeaderRepository, RequestDetailRepository requestDetailRepository, RequestHeaderDao requestHeaderDao) {
        this.requestHeaderRepository = requestHeaderRepository;
        this.requestDetailRepository = requestDetailRepository;
        this.requestHeaderDao = requestHeaderDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RequestHeader save(RequestHeader entity) {
        return requestHeaderRepository.save(entity);
    }

    @Override
    public RequestDetail save(RequestDetail entity) {
        return requestDetailRepository.save(entity);
    }

    @Override
    public List<RequestHeader> getRequestHeaderList() {
        return requestHeaderDao.getList(
                List.of(RequestType.RETURN,
                        RequestType.MISS,
                        RequestType.REPAIR,
                        RequestType.MODIFY,
                        RequestType.CHANGESIZE,
                        RequestType.FINISHANDCLEAN,
                        RequestType.OTHER,
                        RequestType.NEWCARRIER));
    }

    @Override
    public RequestHeader getById(long id) {
        return requestHeaderRepository.findById(id).orElse(null);
    }

    @Override
    public RequestDetail getRequestDetailById(long id) {
        return requestDetailRepository.getRequestDetailById(id);
    }


    @Override
    public List<RequestHeader> getImprovementRequestList() {
        return IteratorUtils.toList(requestHeaderRepository.findAllByRequestDetailTypOrderByDateDescIdDesc(RequestType.IMPROVEMENT).iterator());
    }

    @Override
    public List<RequestDetail> getListDetail(RequestFlag flag, RequestType type) {
        return requestDetailRepository.getListDetail(flag, type);
    }

    @Override
    public List<Object[]> getNativeListDetail(RequestFlag flag, RequestType type) {
        return requestHeaderDao.getNativeListDetail(flag, type);
    }

    @Override
    public List<Object[]> getNativeListDetail(RequestFlag flag) {
        return requestHeaderDao.getNativeListDetail(flag);
    }

    @Override
    public List<RequestDetail> getListDetailAccepted(RequestFlag flag) {
        return requestDetailRepository.getListDetailAccepted(flag);
    }

    @Override
    public List<RequestDetail> getUserListDetail(String userMail) {
        return requestDetailRepository.getUserListDetail(userMail);
    }

    @Override
    public List<Object[]> getNativeUserListDetail(String userMail) {
        return requestHeaderDao.getNativeUserListDetail(userMail);
    }
}
