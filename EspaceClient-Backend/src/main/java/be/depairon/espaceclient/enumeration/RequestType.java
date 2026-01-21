package be.depairon.espaceclient.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum RequestType {
    NEWCARRIER,
    IMPROVEMENT,
    RETURN,
    MISS,
    REPAIR,
    MODIFY,
    CHANGESIZE,
    FINISHANDCLEAN,
    OTHER,
    UNKNOWN;


    public static String getRequestValueType(RequestType typ) {
        Map<RequestType, String> typMap = new HashMap<>();

        typMap.put(RequestType.NEWCARRIER, "Nouveau Porteur");
        typMap.put(RequestType.IMPROVEMENT, "Amélioration");
        typMap.put(RequestType.RETURN, "Rentrée");
        typMap.put(RequestType.MISS, "Vêtements manquants");
        typMap.put(RequestType.REPAIR, "Réparation");
        typMap.put(RequestType.CHANGESIZE, "Changer la Taille");
        typMap.put(RequestType.MODIFY, "Modification");
        typMap.put(RequestType.FINISHANDCLEAN, "Finition et propreté");
        typMap.put(RequestType.OTHER, "Autres");
        typMap.put(RequestType.UNKNOWN, "Inconnu");

        return typMap.get(typ);

    }
}
