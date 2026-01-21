package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.DotationController;
import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.dto.DotationDTO;
import be.depairon.espaceclient.eglobal.utils.MarquageTraitement;
import be.depairon.espaceclient.eglobal.utils.STR;
import be.depairon.espaceclient.entity.CusXdc;
import be.depairon.espaceclient.entity.Dotation;
import be.depairon.espaceclient.service.CusXdcService;
import be.depairon.espaceclient.service.DotationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DotationControllerImpl extends GenericController implements DotationController {

    private final DotationService dotationService;
    private final CusXdcService cusXdcService;

    @Autowired
    public DotationControllerImpl(DotationService dotationService, CusXdcService cusXdcService) {
        this.dotationService = dotationService;
        this.cusXdcService = cusXdcService;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getList(String customer, String token) {
        Map<String, Object> items = new HashMap<>();
        List<CusXdc> cusXdcList = cusXdcService.listCusXdc(customer, 0);
        if (cusXdcList.size() > 0) {
            CusXdc cusXdc = cusXdcList.get(0);
            items.put("displayDate", cusXdc.getCusMiseEnService());
        }
        List<Dotation> dotations = dotationService.getList(customer);
        List<DotationDTO> dotationDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dotations)) {
            dotationDTOList = dotations.stream().toList().stream().map(this::convert).toList();
        }
        items.put("list", dotationDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    public DotationDTO convert(Dotation dotation) {
        DotationDTO dotationDTO = new DotationDTO();
        dotationDTO.setId(dotation.getId());
        dotationDTO.setClient(dotation.getClient().trim());
        dotationDTO.setAdresse(dotation.getAdresse());
        dotationDTO.setNomClient(dotation.getNomClient().trim());
        String barcodeNew = "";
        long barcode = 0;
        if (dotation.getBarcode() != 0) {
            barcode = dotation.getBarcode();
            barcodeNew = barcode + MarquageTraitement.getBarcodeDigit(STR.Complete(barcode + "", 7));
            try {
                barcode = Long.parseLong(barcodeNew);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        dotationDTO.setBarcode(barcode);
        dotationDTO.setArmoire(dotation.getArmoire());
        dotationDTO.setCassette(dotation.getCassette());
        String nature = StringUtils.trim(dotation.getNature());
        dotationDTO.setNature(getNaturePorteurText(nature));
        dotationDTO.setPorteur(StringUtils.trim(dotation.getPorteur()));
        dotationDTO.setArticle(dotation.getArticle().trim());
        String dotDes = dotation.getDescription().trim();
        dotationDTO.setDescription(dotDes);
        //
       /*List<CusXdc> cusXdcList = cusXdcService.listCusXdc(dotation.getClient(), dotation.getAdresse());
        if (cusXdcList.size() > 0) {
            CusXdc cusXdc = cusXdcList.get(0);
            dotationDTO.setDisplayDate(cusXdc.getCusMiseEnService());
        }*/
        boolean isSacDotation = StringUtils.containsIgnoreCase(dotDes, "SAC ");
        String miseService = dotation.getMiseService() == 0 || isSacDotation
                ? "" : String.valueOf(dotation.getMiseService());
        dotationDTO.setMiseService(miseService);
        dotationDTO.setDernierPassage(dotation.getDateDernierPassage());
        dotationDTO.setStop(dotation.getStop().trim());
        dotationDTO.setDateIn(!isSacDotation ? dotation.getDateIn() : null);
        dotationDTO.setDateOUT(!isSacDotation ? dotation.getDateOUT() : null);

        return dotationDTO;
    }

}
