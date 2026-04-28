package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.ResetPasswordDTO;
import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.dto.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.depairon.espaceclient.dto.AdminResetPasswordDTO;

import java.util.List;

@RequestMapping("/users")
public interface UserController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDTO> getList(@RequestParam("userMail") String userMail, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO get(@RequestParam("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO getById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@RequestBody UserDTO userDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody UserCreationDTO userDTO);

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(path = "/password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> forgotPassword(@RequestBody UserDTO userDTO);

    @PostMapping(path = "/password/reset", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO);

    @GetMapping(path = "/password/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> validateToken(@RequestParam String token);

    @PostMapping(path = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> adminResetPassword(@PathVariable Long id,
                                         @RequestBody AdminResetPasswordDTO adminResetPasswordDTO,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token);


}