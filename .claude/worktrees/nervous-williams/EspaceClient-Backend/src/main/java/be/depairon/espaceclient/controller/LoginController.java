package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.configuration.JwtRequest;
import be.depairon.espaceclient.dto.LoginFormDTO;
import be.depairon.espaceclient.dto.LoginRemoteDTO;
import be.depairon.espaceclient.dto.WebUserLoginDTO;
import be.depairon.espaceclient.dto.WebUserRemoteDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
public interface LoginController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    WebUserLoginDTO login(@RequestBody LoginFormDTO loginForm);

    @PostMapping(path = "/remote",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    WebUserRemoteDTO loginFromRemote(@RequestBody LoginRemoteDTO loginRemoteDTO);
}
