package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.entity.RequestHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestHeaderDTO {
    private long id;
    private int cupboard;
    private int cassette;
    private String carrier;
    private String site;
    private int artQuantity;
    private List<RequestDetailDTO> requestDetailDTO;
    private CustomerDTO customerDTO;
    private String user;
    private String ticket;
    private LocalDate date;
    private String gestionnaire;
}
