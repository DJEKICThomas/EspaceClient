package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RemoteOperationsDTO {
    private String filter;
    private String group;
    private String groupSummary;
    private Boolean isExport;
    private Long skip;
    private String sort;
    private Long take;
    private String totalSummary;
}
