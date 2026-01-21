package be.depairon.espaceclient.converter;

import be.depairon.espaceclient.dto.CustomerDTO;
import be.depairon.espaceclient.dto.RequestHeaderDTO;

import be.depairon.espaceclient.dto.*;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EntityConverter {

    public RequestHeaderDTO convertToDTO(RequestHeader requestHeader) {
        RequestHeaderDTO dto = new RequestHeaderDTO();
        if (requestHeader != null) {
            dto.setId(requestHeader.getId());
            dto.setCupboard(requestHeader.getCupboard());
            dto.setCassette(requestHeader.getCassette());
            dto.setCarrier(requestHeader.getCarrier());
            dto.setSite(requestHeader.getSite());
            dto.setArtQuantity(requestHeader.getRequestDetail().size());
            Customer customer = requestHeader.getCustomer();
            if (customer != null) {
                dto.setCustomerDTO(this.convertToDTO(customer));
                List<CusSal> cusSal = customer.getCusSal();
                CusSal c = cusSal.stream().findFirst().orElse(null);
                dto.setGestionnaire(c != null ? c.getCusSalRes() : "");
            }
            // dto.setRequestDetailDTO(requestHeader.getRequestDetail().stream().map(this::convertToDTO).toList());
        }
        return dto;
    }

    public RequestDetailGestDTO convertToDTO(RequestDetail requestDetail) {
        RequestDetailGestDTO requestDetailGestDTO = new RequestDetailGestDTO();
        requestDetailGestDTO.setId(requestDetail.getId());
        RequestHeader requestHeader = requestDetail.getRequestHeader();
        if (requestHeader != null) {
            requestDetailGestDTO.setCupboard(requestHeader.getCupboard());
            requestDetailGestDTO.setCassette(requestHeader.getCassette());
            requestDetailGestDTO.setCarrier(requestHeader.getCarrier());
            requestDetailGestDTO.setDate(requestHeader.getDate().toLocalDate());
            requestDetailGestDTO.setDemandeur(requestHeader.getUser());
            requestDetailGestDTO.setTexte(requestDetail.getText());
            requestDetailGestDTO.setTyp(requestDetail.getTyp());
            requestDetailGestDTO.setTypDes(RequestType.getRequestValueType(requestDetail.getTyp()));
            requestDetailGestDTO.setTextGes(requestDetail.getTextGes());
            Customer customer = requestHeader.getCustomer();
            requestDetailGestDTO.setTicket(requestHeader.getTicket());
            if (customer != null) {
                requestDetailGestDTO.setSite(customer.getCusNm1());
                List<CusSal> cusSal = customer.getCusSal();
                if (CollectionUtils.isNotEmpty(cusSal)) {
                    requestDetailGestDTO.setRep(cusSal.get(0).getCusSalRes());
                }
            }
            requestDetailGestDTO.setStatusDes(RequestFlag.getRequestValueFlag(requestDetail.getFlag()));
            List<Barcode> barcodeList = requestDetail.getBarcodeList();
            if (CollectionUtils.isNotEmpty(barcodeList)) {
                StringBuilder barcode = new StringBuilder("Liste des Barcodes : ");
                for (Barcode bcr : barcodeList) {
                    barcode.append(bcr.getBarcode()).append("   ");
                }
                requestDetailGestDTO.setBarcodeList(barcode.toString());

            }
        }
        return requestDetailGestDTO;


    }

   /* public RequestDetailDTO convertToDTO(RequestDetail requestDetail) {
        RequestDetailDTO requestDetailDTO = new RequestDetailDTO();
        if (requestDetail != null) {
            requestDetailDTO.setId(requestDetail.getId());
            requestDetailDTO.setText(requestDetail.getText());
            requestDetailDTO.setTyp(String.valueOf(requestDetail.getTyp()));
            requestDetailDTO.setReturnDate(requestDetail.getReturndate());
            requestDetailDTO.setArticleDTO(this.convertToDTO(requestDetail.getArticle()));

            // Quand on transforme l'entité en DTO, on ne va jamais plus bas pour eviter d'envoyer trop de données d'un coup
            // List<Barcode> barcodeList = requestDetail.getBarcodeList();
            // if (barcodeList != null)
            //    requestDetailDTO.setBarcodeList(barcodeList.stream().map(this::convertToDTO).toList());
        }
        return requestDetailDTO;
    }*/


    public CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer != null) {
            customerDTO.setId(customer.getId());
            customerDTO.setCusNcs(customer.getCusNcs());
            customerDTO.setCusNda(customer.getCusNda());
            customerDTO.setCusNm1(customer.getCusNm1());
            customerDTO.setCusNm2(customer.getCusNm2());
        }
        return customerDTO;
    }

    public ArtDTO convertToDTO(Art article) {
        ArtDTO artDTO = new ArtDTO();
        if (article != null) {
            artDTO.setId(article.getId());
            artDTO.setArtIin(article.getArtIin());
            article.getDescriptions().stream()
                    .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                    .findFirst().ifPresent(artDes -> artDTO.setDescription(StringUtils.trim(artDes.getArtDesDes())));
        }
        return artDTO;
    }

    public ArtBarcodeDTO convertToDetailDTO(Art article) {
        ArtBarcodeDTO artBarcodeDTO = new ArtBarcodeDTO();
        if (article != null) {
            artBarcodeDTO.setId(article.getId());
            artBarcodeDTO.setArtIin(article.getArtIin());
            article.getDescriptions().stream()
                    .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                    .findFirst().ifPresent(artDes -> artBarcodeDTO.setDescription(StringUtils.trim(artDes.getArtDesDes())));
        }
        return artBarcodeDTO;
    }

    public BarcodeDTO convertToDTO(Barcode barcode) {
        BarcodeDTO barcodeDTO = new BarcodeDTO();
        if (barcode != null) {
            barcodeDTO.setId(barcode.getId());
            barcodeDTO.setBarcode(String.valueOf(barcode.getBarcode()));
            try {
                barcodeDTO.setPorteurId(barcode.getPorteur().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return barcodeDTO;
    }

    public BarcodeDetailDTO convertToDetailDTO(Barcode barcode) {
        BarcodeDetailDTO barcodeDTO = new BarcodeDetailDTO();
        if (barcode != null) {
            barcodeDTO.setId(barcode.getId());
            barcodeDTO.setBarcode(String.valueOf(barcode.getBarcode()));
            try {
                barcodeDTO.setPorteurId(barcode.getPorteur().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return barcodeDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setCusNcs(customerDTO.getCusNcs());
        customer.setCusNda(customerDTO.getCusNda());
        customer.setCusNm1(customerDTO.getCusNm1());
        customer.setCusNm2(customerDTO.getCusNm2());
        return customer;
    }

    public RequestHeader convertToEntity(RequestHeaderDTO requestHeaderDTO) {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setUser(StringUtils.defaultIfBlank(requestHeaderDTO.getUser(), ""));
        requestHeader.setDate(LocalDateTime.now());
        requestHeader.setCassette(requestHeaderDTO.getCassette());
        requestHeader.setCupboard(requestHeaderDTO.getCupboard());
        requestHeader.setCarrier(requestHeaderDTO.getCarrier());
        requestHeader.setSite(requestHeaderDTO.getSite());

        List<RequestDetailDTO> requestDetailDTO = requestHeaderDTO.getRequestDetailDTO();
        for (RequestDetailDTO detailDTO : requestDetailDTO) {
            RequestDetail detail = detailDTO.convert();
            List<BarcodeDTO> barcodeList = detailDTO.getBarcodeList();
            if (CollectionUtils.isNotEmpty(barcodeList)) {
                for (BarcodeDTO barcodeDTO : barcodeList) {
                    Barcode barcode = barcodeDTO.convert();
                    detail.addBarcodeToList(barcode);
                }
            }
            requestHeader.addRequestDetailToList(detail);
        }
        CustomerDTO customerDTO = requestHeaderDTO.getCustomerDTO();
        if (customerDTO != null) {
            Customer customer = this.convertToEntity(customerDTO);
            requestHeader.setCustomer(customer);
        }
        return requestHeader;
    }

    public PorteurDTO convertToDTO(Porteur porteur) {
        PorteurDTO porteurDTO = new PorteurDTO();
        if (porteur != null) {
            porteurDTO.setId(porteur.getId());
            porteurDTO.setCarrier(porteur.getCusPortNom());
            porteurDTO.setSite(porteur.getCusPortSite());
            // TODO compléter
        }
        return porteurDTO;
    }
}
