package be.depairon.espaceclient.security;

import be.depairon.espaceclient.configuration.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdminAccessChecker {

    private static final String ADMIN_PROFIL = "ADMINISTRATEUR";
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AdminAccessChecker(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /* Vérif si le token appartient à un admin */
    public void requireAdmin(String authorizationHeader){
        String token = extractToken(authorizationHeader);

        String profile;
        try{
            profile = jwtTokenUtil.getClaimFromToken(token, claims -> {
                Object value = claims.get("userprofile");
                return value != null ? value.toString() : null;
            });
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide");
        }

        if(!ADMIN_PROFIL.equals(profile)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès refusé : profil administrateur requis");
        }
    }

    private String extractToken(String authorizationHeader){
        if(StringUtils.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token manquant");
        }

        return authorizationHeader.substring(7);
    }



}
