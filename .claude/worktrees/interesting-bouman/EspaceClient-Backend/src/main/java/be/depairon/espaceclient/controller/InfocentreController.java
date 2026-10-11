package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.InfocentreDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/infocentre")
public interface InfocentreController {

    @GetMapping("/user")
    List<InfocentreDTO> getDocs(@RequestParam String userId, @RequestParam(value = "ncs", required = false) String ncs);
}
