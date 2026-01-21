package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.NettoyageController;
import be.depairon.espaceclient.dto.NettoyageDTO;
import be.depairon.espaceclient.dto.RemoteListDTO;
import be.depairon.espaceclient.dto.RemoteOperationsDTO;
import be.depairon.espaceclient.eglobal.utils.MarquageTraitement;
import be.depairon.espaceclient.eglobal.utils.STR;
import be.depairon.espaceclient.eglobal.utils.devextreme.QueryBuilder;
import be.depairon.espaceclient.service.EntryControlService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NettoyageControllerImpl extends GenericController implements NettoyageController {

    private final Logger log = LoggerFactory.getLogger(NettoyageControllerImpl.class);

    private final EntryControlService entryControlService;

    public NettoyageControllerImpl(EntryControlService entryControlService) {
        this.entryControlService = entryControlService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NettoyageDTO> getList(String customer, String token) {
        List<NettoyageDTO> nettoyageDTOList = new ArrayList<>();
        try {
            List<Object[]> entryControls = entryControlService.getNativeList(customer);
            if (CollectionUtils.isNotEmpty(entryControls)) {
                nettoyageDTOList = entryControls.stream().map(this::convert).toList();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return nettoyageDTOList;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public ResponseEntity<?> getList(String customer, RemoteOperationsDTO remoteOperations) {
//        RemoteListDTO remoteListDTO = new RemoteListDTO();
//        List<NettoyageDTO> nettoyageDTOList = new ArrayList<>();
//        Map<String, String> convertTable = generateConvertTable();
//
//        QueryBuilder queryBuilder = new QueryBuilder(objectMapper);
//        queryBuilder.initializeConditionList(remoteOperations.getFilter());
//        queryBuilder.initializeSortList(remoteOperations.getSort(), convertTable);
//
//        String whereClause = queryBuilder.buildWhereClause();
//        String orderByClause = queryBuilder.buildOrderByClause();
//
//        for (String key : convertTable.keySet()) {
//            whereClause = whereClause.replaceAll(key, convertTable.get(key));
//        }
//
//        List<Object[]> entryControls = this.entryControlService.getNativeList(customer, remoteOperations.getSkip(), remoteOperations.getTake(), whereClause, orderByClause);
//        if (CollectionUtils.isNotEmpty(entryControls)) {
//            nettoyageDTOList = entryControls.stream().map(this::convert).toList();
//        }
//
//        remoteListDTO.setData(nettoyageDTOList);
//        remoteListDTO.setTotalCount(entryControlService.countNativeList(customer, whereClause));
//        remoteListDTO.setKey("nettoyageListKey");
//
//        return ResponseEntity.ok(remoteListDTO);
//    }

    private Map<String, String> generateConvertTable() {
        Map<String, String> convertNativeMap = new HashMap<>();
        convertNativeMap.put("id", "ec.ID");
        convertNativeMap.put("site", "cus.CUS_NM1");
        convertNativeMap.put("carrier", "EC.CARRIER");
        convertNativeMap.put("artDes", "artDes.ART_DES_DES");
        convertNativeMap.put("date", "ec.DATE");
        convertNativeMap.put("cupboard", "ec.cupboard");
        convertNativeMap.put("cassette", "ec.cassette");
        convertNativeMap.put("barcode", "ec.barcode");
        convertNativeMap.put("nature", "bar.lovepe");
        return convertNativeMap;
    }

    private NettoyageDTO convert(Object[] item) {
        NettoyageDTO nettoyageDTO = new NettoyageDTO();
        long id = ((BigInteger) item[0]).longValue();
        nettoyageDTO.setId(id);
        String cusName = (String) item[1];
        nettoyageDTO.setSite(StringUtils.trim(cusName));
        String porteur = (String) item[2];
        nettoyageDTO.setCarrier(StringUtils.trim(porteur));
        String artDes = (String) item[3];
        nettoyageDTO.setArtDes(StringUtils.trim(artDes));
        Timestamp date = (Timestamp) item[4];
        if (date != null) {
            nettoyageDTO.setDate(date.toLocalDateTime().toLocalDate());
        }
        int cupboard = ((BigDecimal) item[5]).intValue();
        nettoyageDTO.setCupboard(cupboard);
        int cassette = ((BigDecimal) item[6]).intValue();
        nettoyageDTO.setCassette(cassette);
        long barcode = ((BigDecimal) item[7]).longValue();
        String barcode8Digits = barcode + MarquageTraitement.getBarcodeDigit(STR.Complete(barcode + "", 7));
        nettoyageDTO.setBarcode(Long.parseLong(barcode8Digits));
        String lovepe = String.valueOf(item[8]);
        nettoyageDTO.setNature(getNaturePorteurText(StringUtils.trim(lovepe)));
        nettoyageDTO.setName((String) item[9]);
        return nettoyageDTO;

    }

}
