package be.depairon.espaceclient.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum RequestFlag {
    //ACCEPTED("ACCEPTED"),
    REFUSED("REFUSED"),// 20 après refusé au filtrage niveau 2
    CONSIDERED("CONSIDERED"), // 10 après validé au filtrage niveau 1
    NOTTREATED("NOTTREATED"),
    NOTCONSIDERED("NOTCONSIDERED"), //0 création de la demande par pho (prendre ca au niveau 1 et requestType!=IMPROVEMENT
    TREATED("TREATED"),// 30 après validé au filtrage niveau 2
   // NOTTREATED("NOTTREATED"),
    UNKNOWN("UNKNOWN");

    public final String label;

    RequestFlag(String label) {
        this.label = label;
    }

    public static RequestFlag valueOfLabel(String label) {
        for (RequestFlag e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return UNKNOWN;
    }

    public static String getRequestValueFlag(RequestFlag flag) {
        Map<RequestFlag, String> flagMap = new HashMap<>();

        flagMap.put(RequestFlag.REFUSED, "En cours");
        flagMap.put(RequestFlag.CONSIDERED, "En cours");
        flagMap.put(RequestFlag.NOTTREATED, "En cours");
        flagMap.put(RequestFlag.NOTCONSIDERED, "En cours");
        flagMap.put(RequestFlag.TREATED, "Traitée");
        flagMap.put(RequestFlag.UNKNOWN, "Inconnu");

        return flagMap.get(flag);

    }
}
