package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class DocAttDTO {

    private String docNumber; //DocDok
    private String filePath;  //DocAtt
    private String fileName;

}
