package be.depairon.espaceclient.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum WebUserProfile {

    ADMINISTRATEUR,
    UTILISATEUR,
    COMMERCIAL,
    GESTIONNAIRE,
    DEPAIRON,
    INCONNU;

    public static WebUserProfile getWebUserProfile(String profile) {
        Map<String, WebUserProfile> typMap = new HashMap<>();

        typMap.put("ADMINISTRATEUR", WebUserProfile.ADMINISTRATEUR);
        typMap.put("UTILISATEUR", WebUserProfile.UTILISATEUR);
        typMap.put("COMMERCIAL", WebUserProfile.COMMERCIAL);
        typMap.put("GESTIONNAIRE", WebUserProfile.GESTIONNAIRE);
        typMap.put("DEPAIRON", WebUserProfile.DEPAIRON);
        typMap.put("INCONNU", WebUserProfile.INCONNU);

        return typMap.getOrDefault(profile, WebUserProfile.INCONNU);

    }

}