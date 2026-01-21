package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.PorteurController;
import be.depairon.espaceclient.converter.EntityConverter;
import be.depairon.espaceclient.dto.*;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.service.BarcodeService;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.PorteurService;
import be.depairon.espaceclient.service.WebNominativeManagementService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class PorteurControllerImpl extends GenericController implements PorteurController {

    private final Logger log = LoggerFactory.getLogger(PorteurControllerImpl.class);
    private final WebNominativeManagementService webNominativeManagementService;
    private final PorteurService porteurService;
    private final BarcodeService barcodeService;
    private final CustomerService customerService;
    private final EntityConverter entityConverter;

    public PorteurControllerImpl(WebNominativeManagementService webNominativeManagementService, PorteurService porteurService, BarcodeService barcodeService, CustomerService customerService, EntityConverter entityConverter) {
        this.webNominativeManagementService = webNominativeManagementService;
        this.porteurService = porteurService;
        this.barcodeService = barcodeService;
        this.customerService = customerService;
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PorteurDTO> getList(String customer, String token) {
        List<Object[]> list = webNominativeManagementService.getNativeList(customer);
        List<PorteurDTO> porteurDTOList = convert(list);
        if (CollectionUtils.isNotEmpty(porteurDTOList)) {
            porteurDTOList = porteurDTOList.stream()
                    .sorted(Comparator.comparing(PorteurDTO::getCarrier).thenComparing(PorteurDTO::getArtDes))
                    .toList();
        }
        return porteurDTOList;
    }

    private List<PorteurDTO> convert(List<Object[]> list) {
        List<PorteurDTO> porteurDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            porteurDTOList = list.stream().map(porteur -> {
                PorteurDTO porteurDTO = new PorteurDTO();
                porteurDTO.setId(((BigInteger) porteur[0]).longValue());
                porteurDTO.setSite(((String) porteur[1]).trim());
                porteurDTO.setCarrier(((String) porteur[2]).trim());
                porteurDTO.setCupboard(Integer.parseInt(((String) porteur[3]).trim()));
                porteurDTO.setCassette(Integer.parseInt(((String) porteur[4]).trim()));
                porteurDTO.setArtDes(((String) porteur[5]).trim());
                porteurDTO.setQuantity(((BigDecimal) porteur[6]).intValue());
                porteurDTO.setNcsId(((BigInteger) porteur[7]).longValue());
                porteurDTO.setArtId(((BigInteger) porteur[8]).longValue());
                porteurDTO.setNature(((String) porteur[9]).trim());
                porteurDTO.setName(((String) porteur[10]).trim());
                return porteurDTO;
            }).toList();
        }
        return porteurDTOList;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getDetail(Long cusId, String carrier, String token) {
        List<Object[]> details = webNominativeManagementService.getNativeListByPorteur(cusId, carrier);
        if (CollectionUtils.isNotEmpty(details)) {
            NewRequestDTO dto = new NewRequestDTO();
            dto.setSite(StringUtils.trim((String) details.get(0)[1]));
            dto.setCarrier(StringUtils.trim((String) details.get(0)[2]));
            dto.setCupboard(StringUtils.trim((String) details.get(0)[3]));
            dto.setCassette(StringUtils.trim((String) details.get(0)[4]));
            dto.setCarrierName(StringUtils.trim((String) details.get(0)[10]));
            dto.setNcsId(((BigInteger) details.get(0)[7]).longValue());
            List<NewRequestDetailDTO> detailDTOList = new ArrayList<>();
            for (Object[] row : details) {
                NewRequestDetailDTO newRequestDetailDTO = new NewRequestDetailDTO();
                newRequestDetailDTO.setNominId(((BigInteger) row[0]).longValue());
                newRequestDetailDTO.setQty(((BigDecimal) row[6]).intValue());
                newRequestDetailDTO.setArtDes(StringUtils.trim((String) row[5]));
                newRequestDetailDTO.setNature(StringUtils.trim(((Character) row[9]).toString()));
                detailDTOList.add(newRequestDetailDTO);
            }
            dto.setNewRequestDetailDTOList(detailDTOList);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun porteur trouvé"));
/*        List<NominativeManagement> list = webNominativeManagementService.getListByPorteur(cusId, carrier);
        NewRequestDTO dto = new NewRequestDTO();
        if (CollectionUtils.isNotEmpty(list)) {
            NominativeManagement nominativeManagement = list.get(0);
            PorteurDTO porteurDTO = this.convert(nominativeManagement);

            Customer customer = nominativeManagement.getCustomer();
            if (customer != null) {
                Porteur portId = porteurService.getDetail(customer.getCusNcs(), customer.getCusNda(), nominativeManagement.getCarrier());
                porteurDTO.setPorteurId(portId.getId());
                List<Barcode> allBarcode = barcodeService.getBarcodeList(portId.getId());
                List<Barcode> barcodeList = removeDuplicateArticles(allBarcode);
                if (CollectionUtils.isNotEmpty(barcodeList)) {
                    List<BarcodeDetailDTO> barcodeDToList = barcodeList.stream().map(b -> convertDetail(b, allBarcode)).toList();
                    dto.setPorteurDTO(porteurDTO);
                    dto.setBarcodeDTOList(barcodeDToList);
                    return ResponseEntity.status(HttpStatus.OK).body(dto);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun porteur trouvé"));*/
    }

    private List<Barcode> removeDuplicateArticles(List<Barcode> barcodeList) {
        List<Barcode> newBarcodeList = new ArrayList<>();
        for (Barcode b : barcodeList) {
            boolean isAlreadyInList = false;
            for (Barcode newBarcode : newBarcodeList) {
                if (b.getArticle().getArtIin() == newBarcode.getArticle().getArtIin()) {
                    isAlreadyInList = true;
                    break;
                }
            }
            if (!isAlreadyInList) {
                newBarcodeList.add(b);
            }
        }
        return newBarcodeList;
    }

    private BarcodeDTO convert(Barcode barcode) {
        BarcodeDTO barcodeDTO = entityConverter.convertToDTO(barcode);
        barcodeDTO.setArtDTO(entityConverter.convertToDTO(barcode.getArticle()));
        return barcodeDTO;
    }

    private BarcodeDetailDTO convertDetail(Barcode barcode, List<Barcode> allSubBarcode) {
        BarcodeDetailDTO barcodeDTO = entityConverter.convertToDetailDTO(barcode);
        barcodeDTO.setArtBarcodeDTO(entityConverter.convertToDetailDTO(barcode.getArticle()));
        barcodeDTO.getArtBarcodeDTO().setBarcodeList(allSubBarcode.stream().filter(b -> b.getArticle().getId() == barcode.getArticle().getId()).map(entityConverter::convertToDTO).toList());
        return barcodeDTO;
    }

    private PorteurDTO convert(NominativeManagement nominativeManagement) {
        PorteurDTO porteurDTO = new PorteurDTO();
        try {
            porteurDTO.setId(nominativeManagement.getId());
            porteurDTO.setSite(nominativeManagement.getCustomer().getCusNm1().trim());
            porteurDTO.setCarrier(nominativeManagement.getCarrier());
            porteurDTO.setCassette(nominativeManagement.getCassette());
            porteurDTO.setCupboard(nominativeManagement.getCupboard());
            porteurDTO.setSite(nominativeManagement.getCustomer().getCusNm1().trim());
            porteurDTO.setNature(getNaturePorteurText(nominativeManagement.getNature()));
            List<ArtDes> descriptions = nominativeManagement.getArticle().getDescriptions();
            ArtDes artDes = descriptions.stream()
                    .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                    .findFirst().orElse(null);
            if (artDes != null) {
                String article = artDes.getArtDesDes();
                porteurDTO.setArtDes(article);
            }
            porteurDTO.setQuantity(nominativeManagement.getQuantity());
            porteurDTO.setNcsId(nominativeManagement.getCustomer().getId());
            porteurDTO.setArtId(nominativeManagement.getArticle().getId());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return porteurDTO;
    }

}
