package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String customer;
    private String ncs;
    private int nda;
    private String site;
    private String carrier;
    private String type;
    private String code;
    private int iin;
    private LocalDate date;
    private int quantity;
    private String article;
    private String from;
    private String delivery;
    private String reference;
}
