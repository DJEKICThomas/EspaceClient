package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.PermissionDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/permission")
public interface PermissionController {
    @GetMapping
    ResponseEntity<?> getPermission(@RequestParam("mail") String email,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/list")
    ResponseEntity<?> getPermissionList(@RequestParam("mail") String email,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PutMapping
    ResponseEntity<?> updatePermission(@RequestParam("mail") String email,
                                       @RequestBody PermissionDTO permissionDTO,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}