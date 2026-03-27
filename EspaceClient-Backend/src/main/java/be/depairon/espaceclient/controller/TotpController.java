package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.TotpRequestDTO;
import be.depairon.espaceclient.dto.TotpResponseDTO;
import be.depairon.espaceclient.dto.WebUserLoginDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/totp")
public interface TotpController {

    @PostMapping(path = "/setup",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TotpResponseDTO setup(@RequestBody TotpRequestDTO requestDTO);

    @PostMapping(path = "/verify",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    WebUserLoginDTO verify(@RequestBody TotpRequestDTO requestDTO);

    @PostMapping(path = "/disable",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TotpResponseDTO disable(@RequestBody TotpRequestDTO requestDTO);

    @GetMapping(path = "/status/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TotpResponseDTO status(@PathVariable Long userId);
}
