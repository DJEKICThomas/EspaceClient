package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PorteurArticleDTO {
    private PorteurDTO porteurDTO;
    private List<BarcodeDTO> barcodeDTOList;
}
