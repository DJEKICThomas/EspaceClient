package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PorteurDTO {
    private long id;
    private String site;
    private String carrier;
    private int cupboard;
    private int cassette;
    private String artDes;
    private int quantity;
    private long ncsId;
    private long artId;
    private String nature;
    private long porteurId;
    private String name;
}
