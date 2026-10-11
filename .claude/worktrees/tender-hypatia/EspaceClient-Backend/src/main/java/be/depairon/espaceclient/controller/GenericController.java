package be.depairon.espaceclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class GenericController {

    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected ObjectMapper objectMapper;

    protected LocalDate getLocaleDateFromInt(int date) {
        if (date == 0) {
            return null;
        }
        String dateString = String.valueOf(date);
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(4, 6));
        int day = Integer.parseInt(dateString.substring(6));
        return LocalDate.of(year, month, day);
    }

    protected String getNatureText(String nature) {
        String natureText = "";
        switch (nature) {
            case "L", "LOC":
                natureText = "Location";
                break;
            case "V", "VEN":
                natureText = "Vente";
                break;
            default:
                break;
        }
        return natureText;
    }

    protected String getNaturePorteurText(String nature) {
        String natureText = "";
        switch (nature) {
            case "L", "LOC":
                natureText = "Location";
                break;
            default:
                natureText = "Vente entretien";
                break;
        }
        return natureText;
    }
}
