package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.RequestController;
import be.depairon.espaceclient.converter.EntityConverter;
import be.depairon.espaceclient.dto.*;
import be.depairon.espaceclient.eglobal.utils.MarquageTraitement;
import be.depairon.espaceclient.eglobal.utils.STR;
import be.depairon.espaceclient.eglobal.utils.ThreadMail;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.entity.query.CustomBarcodeStop;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import be.depairon.espaceclient.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@RestController
public class RequestControllerImpl extends GenericController implements RequestController {

    private final Logger log = LoggerFactory.getLogger(RequestControllerImpl.class);

    private final RequestService requestService;
    private final CustomerService customerService;
    private final BarcodeService barcodeService;
    private final EntityConverter entityConverter;
    private final CntService cntService;
    private final MailService mailService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final Object lock = new Object();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RequestControllerImpl(RequestService requestService, CustomerService customerService,
                                 BarcodeService barcodeService, CntService cntService, MailService mailService,
                                 EntityConverter entityConverter, UserService userService, NotificationService notificationService) {
        super();
        this.requestService = requestService;
        this.customerService = customerService;
        this.barcodeService = barcodeService;
        this.cntService = cntService;
        this.mailService = mailService;
        this.entityConverter = entityConverter;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getList(String token) {
//        List<RequestDetail> requestDetails = requestService.getListDetail(RequestFlag.NOTCONSIDERED, RequestType.UNKNOWN);
//        if (CollectionUtils.isNotEmpty(requestDetails)) {
//            List<ResquestDetailGestDTO> dtoList = new ArrayList<>();
//            requestDetails.forEach(r -> {
//                ResquestDetailGestDTO resquestDetailGestDTO = entityConverter.convertToDTO(r);
//                User user = userService.getUser(resquestDetailGestDTO.getDemandeur());
//                resquestDetailGestDTO.setDemandeur(user.getFirstname() + "  " + user.getName());
//                dtoList.add(resquestDetailGestDTO);
//            });
//            return ResponseEntity.status(HttpStatus.OK).body(dtoList);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CollectionUtils.emptyCollection());
//        }
        List<Object[]> requestDetails = requestService.getNativeListDetail(RequestFlag.NOTCONSIDERED, RequestType.UNKNOWN);
        if (CollectionUtils.isNotEmpty(requestDetails)) {
            List<RequestDetailGestDTO> dtoList = convertToDto(requestDetails);
            return ResponseEntity.status(HttpStatus.OK).body(dtoList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CollectionUtils.emptyCollection());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getUserList(String userMail, String token) {
        if (StringUtils.isNotBlank(userMail)) {
//            List<RequestDetail> requestDetails = requestService.getUserListDetail(userMail);
            List<Object[]> requestDetails = requestService.getNativeUserListDetail(userMail);
            if (CollectionUtils.isNotEmpty(requestDetails)) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToDto(requestDetails));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucune requête trouvée"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Adresse mail non reçue"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getDetailList(long id, String token) {
        RequestDetail requestDetail = requestService.getRequestDetailById(id);
        if (requestDetail != null) {
            RequestDetailGestDTO requestDetailDTO = entityConverter.convertToDTO(requestDetail);
            return ResponseEntity.status(HttpStatus.OK).body((requestDetailDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Request not found"));
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> createRequest(RequestHeaderDTO requestHeaderDTO, String token) {
        synchronized (lock) {
            RequestHeader requestHeader = entityConverter.convertToEntity(requestHeaderDTO);
            LocalDateTime now = LocalDateTime.now();
            Customer customer = requestHeader.getCustomer();
            if (customer != null) {
                Customer cus;
                if (customer.getId() != null && customer.getId() > 0) {
                    cus = customerService.getById(customer.getId());
                } else {
                    cus = customerService.getByNcsNda(customer.getCusNcs(), 0);
                }
                requestHeader.setCustomer(cus);
            }/* else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Pas de client trouvé pour créer cette demande"));
            }*/
            List<RequestDetail> detailList = requestHeader.getRequestDetail();
            List<RequestDetail> detailsToRemove = new ArrayList<>();
            for (RequestDetail requestDetail : detailList) {
                requestDetail.setFlag(RequestFlag.NOTCONSIDERED);
                // En général, si c'est une demande de nouveau porteur, il ne devrait pas y voir d'autre détails
                // Donc, il n'y a qu'une seule boucle, donc isNewCarrier ne devrait être que true dans le cas d'une
                // demande de nouveau porteur et toujours false dans l'autre cas vu que la demande dépend du porteur
                if (requestDetail.getTyp() == RequestType.NEWCARRIER) {
                    requestHeader.setCarrier("NOUVEAU");
                } else if (requestDetail.getTyp() == RequestType.RETURN) {
                    if (StringUtils.isBlank(requestDetail.getText()) || requestDetail.getReturndate() == null) {
                        detailsToRemove.add(requestDetail);
                    }
                } else {
                    if (StringUtils.isBlank(requestDetail.getText())) {
                        detailsToRemove.add(requestDetail);
                    }
                }
            }
            requestHeader.removeFromList(detailsToRemove);
            requestHeader.setDate(now);
//        requestHeader.setUser("TODO"); // TODO mettre email demandeur
            if (requestHeader.getRequestDetail().size() > 0) {
                // Create ticket number here
                requestHeader.setTicket(cntService.createNewRequestTicket(now.toLocalDate()));
                RequestHeader savedHeader = this.requestService.save(requestHeader);
                if (savedHeader != null && savedHeader.getId() != 0) {
                    Customer cus = savedHeader.getCustomer();
                    if (cus != null) {
                        CusSal cusSal = cus.getCusSal().stream().findFirst().orElse(null);
                        if (cusSal != null) {
                            String cusSalRes = cusSal.getCusSalRes();
                            Notification notification = notificationService.getByGestionnaire(cusSalRes);
                            if (notification == null) {
                                notification = new Notification();
                                notification.setGestionnaire(cusSalRes);
                            }
                            notification.setIsNew(1);
                            notification.setDate(now);
                            notificationService.save(notification);
                        }
                    }
                    // TOOD send mail
                    Runnable thread = new ThreadMail(mailService, requestHeader);
                    Thread t1 = new Thread(thread);
                    t1.start();
                    return ResponseEntity.status(HttpStatus.CREATED).body(entityConverter.convertToDTO(savedHeader));
                }
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune requête encodée"));
        }
    }

    @Override
    @Transactional
    public List<BarcodeStopDTO> getCustomerRequestsList(String ncs, String token) {
        return convertCustomBarcode(barcodeService.getListForCustomer(ncs));
    }

    @Override
    @Transactional
    public ResponseEntity<?> getImprovementRequestList(String token) {
        List<RequestDetailGestDTO> dtoList = new ArrayList<>();
//        List<RequestDetail> improvementRequestlist = requestService.getListDetailAccepted(RequestFlag.CONSIDERED);
//        if (CollectionUtils.isNotEmpty(improvementRequestlist)) {
//            improvementRequestlist.forEach(r -> {
//                ResquestDetailGestDTO resquestDetailGestDTO = entityConverter.convertToDTO(r);
//                User user = userService.getUser(resquestDetailGestDTO.getDemandeur());
//                resquestDetailGestDTO.setDemandeur(user.getFirstname() + "  " + user.getName());
//                dtoList.add(resquestDetailGestDTO);
//            });
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(dtoList);
        List<Object[]> improvementRequestlist = requestService.getNativeListDetail(RequestFlag.CONSIDERED);
        if (CollectionUtils.isNotEmpty(improvementRequestlist)) {
            dtoList = convertToDto(improvementRequestlist);
        }
        return ResponseEntity.ok(dtoList);
    }

    //
    @Override
    @Transactional
    public ResponseEntity<?> getDetailCommentRequestList(String requestDetail, String token) {

        ObjectMapper objectMapper = new ObjectMapper();
        RequestDetailMapperDTO requestDetailMapperDTO = null;
        ResponseEntity<?> responseEntity = null;
        RequestDetail requestDetailSave = null;

        try {
            requestDetailMapperDTO = objectMapper.readValue(requestDetail, RequestDetailMapperDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (requestDetailMapperDTO != null) {
            RequestDetail requestDetail1 = requestService.getRequestDetailById(requestDetailMapperDTO.getId());
            // TODO modifier ça, ca peut être dangereux si l'utilisateur encode TRUE
            if (requestDetailMapperDTO.getMessage().trim().equals("TRUE")) {

                if (requestDetail1 != null) {
                    requestDetail1.setFlag(RequestFlag.TREATED);
                    requestDetailSave = this.requestService.save(requestDetail1);
                }

                if (requestDetailSave != null) {
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(entityConverter.convertToDTO(requestDetailSave));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune requête encodée"));
                }
            } else if (requestDetailMapperDTO.getMessage().trim().equals("FALSE")) {
                if (requestDetail1 != null) {
                    requestDetail1.setFlag(RequestFlag.REFUSED);
                    requestDetailSave = this.requestService.save(requestDetail1);
                }

                if (requestDetailSave != null) {
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(entityConverter.convertToDTO(requestDetailSave));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune requête encodée"));
                }

            } else {
                if (requestDetail1 != null) {
                    requestDetail1.setTextGes(requestDetailMapperDTO.getMessage());
                    requestDetail1.setFlag(RequestFlag.CONSIDERED);
                    requestDetailSave = this.requestService.save(requestDetail1);
                }

                if (requestDetailSave != null) {
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(entityConverter.convertToDTO(requestDetailSave));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune requête encodée"));
                }
            }
        }
        return responseEntity;
    }

    private List<BarcodeStopDTO> convertCustomBarcode(List<CustomBarcodeStop> barcodeStops) {
        return barcodeStops.stream()
                .map(this::convertCustomBarcode)
                .toList();
    }

    private BarcodeStopDTO convertCustomBarcode(CustomBarcodeStop customBarcodeStop) {
        BarcodeStopDTO dto = new BarcodeStopDTO();
        try {
            dto.setId(customBarcodeStop.getBs().getId());
            dto.setSite(customBarcodeStop.getSite());
            Barcode barcodeEntity = customBarcodeStop.getBs().getBarcode();
            long barcode = barcodeEntity.getBarcode();
            String barcode8Position = barcode + MarquageTraitement.getBarcodeDigit(STR.Complete(barcode + "", 7));
            dto.setBarcode(barcode8Position);
            dto.setRem(StringUtils.defaultIfBlank(customBarcodeStop.getStops().getRemWeb(), customBarcodeStop.getBs().getRem()));
            dto.setStop(customBarcodeStop.getBs().getStop());
            dto.setUsc(customBarcodeStop.getBs().getUsc());
            dto.setDtb(getLocaleDateFromInt(customBarcodeStop.getBs().getDtb()));
            dto.setDtf(getLocaleDateFromInt(customBarcodeStop.getBs().getDtf()));
            dto.setDtr(getLocaleDateFromInt(customBarcodeStop.getBs().getDtr()));
            Porteur porteur = barcodeEntity.getPorteur();
            if (porteur != null) {
                dto.setPorteur(StringUtils.trim(porteur.getCusPortNom()) + " " + StringUtils.trim(porteur.getCusPortPrenom()));
                dto.setPersvet(StringUtils.trim(porteur.getCusPortPersvet()));
            }
            Art article = barcodeEntity.getArticle();
            if (article != null) {
                article.getDescriptions().stream()
                        .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                        .findFirst().ifPresent(artDes -> dto.setArticle(StringUtils.trim(artDes.getArtDesDes())));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return dto;
    }


    private List<BarcodeStopDTO> convert(List<BarcodeStop> barcodeStops) {
        return barcodeStops.stream()
                .map(this::convert)
                .toList();
    }

    private BarcodeStopDTO convert(BarcodeStop barcodeStop) {
        BarcodeStopDTO dto = new BarcodeStopDTO();
        try {
            dto.setId(barcodeStop.getId());
            Barcode barcodeEntity = barcodeStop.getBarcode();
            long barcode = barcodeEntity.getBarcode();
            String barcode8Position = barcode + MarquageTraitement.getBarcodeDigit(STR.Complete(barcode + "", 7));
            dto.setBarcode(barcode8Position);
            dto.setRem(barcodeStop.getRem());
            dto.setStop(barcodeStop.getStop());
            dto.setUsc(barcodeStop.getUsc());
            dto.setDtb(getLocaleDateFromInt(barcodeStop.getDtb()));
            dto.setDtf(getLocaleDateFromInt(barcodeStop.getDtf()));
            dto.setDtr(getLocaleDateFromInt(barcodeStop.getDtr()));
            Porteur porteur = barcodeEntity.getPorteur();
            if (porteur != null) {
                dto.setPorteur(StringUtils.trim(porteur.getCusPortNom()));
            }
            Art article = barcodeEntity.getArticle();
            if (article != null) {
                article.getDescriptions().stream()
                        .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                        .findFirst().ifPresent(artDes -> dto.setArticle(StringUtils.trim(artDes.getArtDesDes())));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return dto;
    }

    @NotNull
    private List<RequestDetailGestDTO> convertToDto(List<Object[]> requestDetails) {
        List<RequestDetailGestDTO> dtoList = new ArrayList<>();
        requestDetails.forEach(r -> {
            RequestDetailGestDTO requestDetailGestDTO = new RequestDetailGestDTO();
            requestDetailGestDTO.setId(((BigInteger) r[0]).longValue());
            requestDetailGestDTO.setCupboard((Integer) r[1]);
            requestDetailGestDTO.setCassette((Integer) r[2]);
            requestDetailGestDTO.setCarrier((String) r[3]);
            requestDetailGestDTO.setSite((String) r[4]);
            requestDetailGestDTO.setTexte((String) r[5]);
            requestDetailGestDTO.setRep((String) r[6]);
            RequestType typ = RequestType.valueOf((String) r[7]);
            requestDetailGestDTO.setTyp(typ);
            requestDetailGestDTO.setTypDes(RequestType.getRequestValueType(typ));
            requestDetailGestDTO.setTextGes((String) r[8]);
            requestDetailGestDTO.setDate(((Date) r[9]).toLocalDate());
            requestDetailGestDTO.setBarcodeList("");
            requestDetailGestDTO.setTicket((String) r[11]);
            String statusDes = RequestFlag.getRequestValueFlag(RequestFlag.valueOf((String) r[12]));
            requestDetailGestDTO.setDemandeur(r[13] + " " + r[14]);
            requestDetailGestDTO.setStatusDes(statusDes);
            String cusNcs = (String) r[15];
            if (StringUtils.isNotBlank(cusNcs)) {
                int cusNda = ((BigDecimal) r[16]).intValue();
                requestDetailGestDTO.setCusNcs(cusNcs);
                requestDetailGestDTO.setCusNda(cusNda);
            }
            dtoList.add(requestDetailGestDTO);
        });
        return dtoList;
    }

}
