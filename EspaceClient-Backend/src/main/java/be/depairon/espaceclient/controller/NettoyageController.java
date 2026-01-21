package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.NettoyageDTO;
import be.depairon.espaceclient.dto.RemoteOperationsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/nettoyages")
public interface NettoyageController {

    @GetMapping
    List<NettoyageDTO> getList(@RequestParam("customer") String customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

//    @PostMapping
//    ResponseEntity<?> getList(@RequestParam("customer") String customer, @RequestBody RemoteOperationsDTO remoteOperations);

}
